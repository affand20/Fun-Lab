package id.trydev.funlab.ui.main.scan

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.Result
import id.trydev.funlab.R
import id.trydev.funlab.model.Riwayat
import id.trydev.funlab.preferences.AppPreferences
import kotlinx.android.synthetic.main.activity_scan.*
import me.dm7.barcodescanner.core.IViewFinder
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.util.*

class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler, View.OnClickListener {

    private lateinit var mScannerView: ZXingScannerView
    private var isCaptured = false
    private lateinit var firebaseFirestore:FirebaseFirestore
    private lateinit var prefs:AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        firebaseFirestore = FirebaseFirestore.getInstance()
        prefs = AppPreferences(this)

        initScannerView()
        initDefaultView()

    }

    private fun initScannerView() {
        mScannerView = object : ZXingScannerView(this) {
            override fun createViewFinderView(context: Context?): IViewFinder {
                return CustomViewFinderView(context!!)
            }
        }
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        frame_layout.addView(mScannerView)
    }

    override fun onStart() {
        doRequestPermission()
        mScannerView.startCamera()
        super.onStart()
    }

    private fun doRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }

    private fun initDefaultView() {
        progress_wait.visibility = View.GONE
//        result_qr.text = ""
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            100 -> {
                initScannerView()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }

    override fun handleResult(rawResult: Result?) {
        val riwayat = Riwayat(Date(), rawResult?.text)
//        result_qr.text = rawResult?.text
        progress_wait.visibility = View.VISIBLE
        firebaseFirestore.collection("bahan")
            .document(rawResult!!.text)
            .update("jumlah", FieldValue.increment(-1))
            .addOnSuccessListener {
                firebaseFirestore.collection("users")
                    .document(prefs.idAnalis)
                    .collection("riwayat")
                    .add(riwayat)
                    .addOnSuccessListener {
                        resumeScanner()
                        Toast.makeText(this, "Stock berhasil di update!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                        resumeScanner()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                resumeScanner()
            }
    }

    private fun resumeScanner() {
        initDefaultView()
        mScannerView.resumeCameraPreview(this)
    }

    override fun onClick(p0: View?) {
//        if (p0?.id==R.id.result_qr){
//            mScannerView.resumeCameraPreview(this)
//            initDefaultView()
//        }
    }
}
