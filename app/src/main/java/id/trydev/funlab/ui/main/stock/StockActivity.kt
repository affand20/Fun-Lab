package id.trydev.funlab.ui.main.stock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import id.trydev.funlab.R
import id.trydev.funlab.model.Stock
import id.trydev.funlab.ui.main.stock.adapter.StockAdapter
import kotlinx.android.synthetic.main.activity_stock.*

class StockActivity : AppCompatActivity() {

    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var adapter: StockAdapter
    private val listStock:MutableList<Stock> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        firebaseFirestore = FirebaseFirestore.getInstance()
        adapter = StockAdapter(this)

        rv_stok.layoutManager = LinearLayoutManager(this)
        rv_stok.adapter = adapter

        getData()

        tambah_stok.setOnClickListener{
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.layout_tambah_stok, null)
            val progress = dialogLayout.findViewById<ProgressBar>(R.id.progressBar6)
            val nama = dialogLayout.findViewById<TextView>(R.id.nama_bahan)
            val jumlah = dialogLayout.findViewById<TextView>(R.id.jumlah_bahan)

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Tambah Stok")
            builder.setView(dialogLayout)
            builder.setPositiveButton("Tambahkan") { dialogInterface, i ->
                val stock = Stock(nama.text.toString(), jumlah.text.toString().toLong())
                progress.visibility = View.VISIBLE
                firebaseFirestore.collection("bahan")
                    .document(nama.text.toString())
                    .set(stock)
                    .addOnSuccessListener {
                        getData()
                        progress.visibility = View.GONE
                        Toast.makeText(this, "Stock berhasil diupdate", Toast.LENGTH_SHORT).show()
                        dialogInterface.dismiss()
                    }
                    .addOnFailureListener {
                        progress.visibility = View.GONE
                        Toast.makeText(this, "Error ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                        dialogInterface.dismiss()
                    }
            }
            builder.setNeutralButton("Batalkan") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            builder.show()
        }

    }

    private fun getData() {
        rv_stok.visibility = View.GONE
        progressBar5.visibility = View.VISIBLE
        firebaseFirestore.collection("bahan")
            .get()
            .addOnSuccessListener {
                rv_stok.visibility = View.VISIBLE
                progressBar5.visibility = View.GONE
                listStock.clear()
                for (stock in it) {
                    listStock.add(stock.toObject(Stock::class.java))
                }

                Log.d("GET_BAHAN", "$listStock")

                if (listStock.size>0) {
                    adapter.addData(listStock)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Data kosong", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                rv_stok.visibility = View.VISIBLE
                progressBar5.visibility = View.GONE
                Log.d("ERROR", it.localizedMessage)
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}
