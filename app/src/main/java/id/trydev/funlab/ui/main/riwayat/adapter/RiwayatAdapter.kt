package id.trydev.funlab.ui.main.riwayat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.trydev.funlab.R
import id.trydev.funlab.model.Riwayat

class RiwayatAdapter(val context:Context):RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder>() {

    private val listRiwayat:MutableList<Riwayat> = mutableListOf()

    fun addData(listRiwayat:List<Riwayat>) {
        this.listRiwayat.clear()
        this.listRiwayat.addAll(listRiwayat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatViewHolder {
        return RiwayatViewHolder(LayoutInflater.from(context).inflate(R.layout.item_riwayat, parent, false))
    }

    override fun getItemCount(): Int = listRiwayat.size

    override fun onBindViewHolder(holder: RiwayatViewHolder, position: Int) {
        holder.namaBahan.text = listRiwayat[position].bahan
        holder.tanggalAmbil.text = listRiwayat[position].date.toString()
    }

    class RiwayatViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val namaBahan = itemView.findViewById<TextView>(R.id.til_nama_bahan)
        val tanggalAmbil = itemView.findViewById<TextView>(R.id.tanggal_ambil)
    }

}