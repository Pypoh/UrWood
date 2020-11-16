package com.example.urwood.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.urwood.R
import com.example.urwood.repository.model.Product
import com.example.urwood.utils.helper.Currency
import com.google.android.material.textview.MaterialTextView

class ProdukAdapter(val context: Context, var dataSet: List<Product.ProductDetail>) :
    RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(productDetailModel: Product.ProductDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_produk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataProductDetail: Product.ProductDetail = dataSet[position]
        holder.bind(dataProductDetail, onItemClickListener)
        holder.nameTextProduk.text = dataProductDetail.name.toString()
        holder.priceTextProduk.text = Currency.intToRupiah(dataProductDetail.price!!)
        holder.imageProduk.clipToOutline = true

        // Glide
        Glide
            .with(context)
            .load(dataProductDetail.image)
            .centerCrop()
            .placeholder(R.drawable.image_example_lemari)
            .into(holder.imageProduk)

//        holder.imageProduk.setImageResource(dataProductDetail.image!!)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setProductData(data: List<Product.ProductDetail>) {
        this.dataSet = data
    }

    fun getOnItemClickListener(): OnItemClickListener {
        return onItemClickListener
    }

    public fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageProduk: ImageView = itemView.findViewById(R.id.item_image_product)
        var nameTextProduk: MaterialTextView = itemView.findViewById(R.id.item_name_product)
        var priceTextProduk: MaterialTextView = itemView.findViewById(R.id.item_price_productt)

        var cardProduk: CardView = itemView.findViewById(R.id.card_item_product)

        fun bind(model: Product.ProductDetail, listener: OnItemClickListener) {
            cardProduk.setOnClickListener {
                listener.onItemClick(model)
            }
        }
    }
}