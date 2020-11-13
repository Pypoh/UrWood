package com.example.urwood.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.urwood.R
import com.example.urwood.repository.model.Home
import com.google.android.material.textview.MaterialTextView

class ProdukAdapter(context: Context, dataSet: List<Home.Produk>) :
    RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {

    private lateinit var onItemClickListener: AdapterView.OnItemClickListener
    private val context: Context = context
    private var dataset: List<Home.Produk> = dataSet

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataProduk: Home.Produk = dataset[position]
//        holder.nameTextKategori.text = dataProduk.name.toString()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        var imageKategori: ImageView = itemView.findViewById(R.id.item_image_kategori)
//        var nameTextKategori: MaterialTextView = itemView.findViewById(R.id.item_text_kategori)
//        var priceTextKategori: MaterialTextView = itemView.findViewById(R.id.item_text_kategori)
    }

}