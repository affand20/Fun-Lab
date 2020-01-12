package id.trydev.funlab.ui.main.stock.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.trydev.funlab.R
import id.trydev.funlab.model.Stock

class StockAdapter(private val context:Context):RecyclerView.Adapter<StockAdapter.ViewHolder>() {

    private val listStock:MutableList<Stock> = mutableListOf()

    fun addData(listStock: List<Stock>) {
        this.listStock.clear()
        this.listStock.addAll(listStock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_stock, parent, false))
    }

    override fun getItemCount(): Int = listStock.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.namaBahan.text = listStock[position].nama
        holder.sisaBahan.text = String.format(context.resources.getString(R.string.sisa), listStock[position].jumlah)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaBahan = itemView.findViewById<TextView>(R.id.til_nama_bahan)
        val sisaBahan = itemView.findViewById<TextView>(R.id.sisa_bahan)
    }
}