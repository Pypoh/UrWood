package com.example.urwood.ui.product.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.urwood.R
import com.example.urwood.ui.main.home.adapter.AdsSliderAdapter
import com.smarteist.autoimageslider.SliderViewAdapter

class ProductDetailImageAdapter(
    private val context: Context,
    private var dataset: List<String>
) : SliderViewAdapter<ProductDetailImageAdapter.ViewHolder>(){

    override fun getCount(): Int {
        return dataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_image_slider, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder?,
        position: Int
    ) {
        val data = dataset[position]
        data.let {
            if (viewHolder != null) {
                Glide
                    .with(context)
                    .load(it)
//                    .placeholder(R.drawable.image_example_lemari)
                    .into(viewHolder.image!!)
            }
        }
    }

    class ViewHolder(itemView: View?) : SliderViewAdapter.ViewHolder(itemView){
        var item: View? = null
        var image: ImageView? = null

        init {
            item = itemView
            image = itemView!!.findViewById(R.id.item_image_slider)
        }
    }
}