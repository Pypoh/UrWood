package com.example.urwood.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.urwood.R
import com.example.urwood.repository.model.Home
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.item_kategori.view.*

class KategoriAdapter(
    private val context: Context,
    private var dataset: List<Home.Kategori>
) :
    RecyclerView.Adapter<KategoriAdapter.ViewHolder>() {

    private lateinit var onItemClickListener: AdapterView.OnItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_kategori, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: KategoriAdapter.ViewHolder, position: Int) {
        val dataKategori: Home.Kategori = dataset[position]
        holder.textKategori.text = dataKategori.name.toString()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageKategori: ImageView = itemView.findViewById(R.id.item_image_kategori)
        var textKategori: MaterialTextView = itemView.findViewById(R.id.item_text_kategori)

        fun bind() {
            // onclick listener
        }

    }

}