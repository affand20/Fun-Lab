package id.trydev.funlab.ui.main.riwayat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import id.trydev.funlab.R
import id.trydev.funlab.model.Riwayat
import id.trydev.funlab.preferences.AppPreferences
import id.trydev.funlab.ui.main.riwayat.adapter.RiwayatAdapter
import kotlinx.android.synthetic.main.activity_riwayat.*

class RiwayatActivity : AppCompatActivity() {

    private lateinit var pref:AppPreferences
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var adapter:RiwayatAdapter
    private val listRiwayat:MutableList<Riwayat> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat)

        pref = AppPreferences(this)
        firebaseFirestore = FirebaseFirestore.getInstance()
        adapter = RiwayatAdapter(this)

        rv_riwayat.layoutManager = LinearLayoutManager(this)
        rv_riwayat.adapter = adapter

        getData(pref.idAnalis)

    }

    private fun getData(idAnalis:String) {
        progressBar4.visibility = View.VISIBLE
        text_state.visibility = View.GONE
        firebaseFirestore.collection("users")
            .document(idAnalis)
            .collection("riwayat")
            .get()
            .addOnSuccessListener {
                progressBar4.visibility = View.GONE
                text_state.visibility = View.VISIBLE

                for (riwayat in it) {
                    listRiwayat.add(riwayat.toObject(Riwayat::class.java))
                }

                if (listRiwayat.size>0) {
                    rv_riwayat.visibility = View.VISIBLE
                    text_state.visibility = View.GONE
                    adapter.addData(listRiwayat)
                    adapter.notifyDataSetChanged()
                } else {
                    rv_riwayat.visibility = View.GONE
                    text_state.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}
