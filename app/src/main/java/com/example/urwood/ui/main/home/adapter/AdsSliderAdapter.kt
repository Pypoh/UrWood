package com.example.urwood.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.urwood.R
import com.example.urwood.repository.model.Home
import com.smarteist.autoimageslider.SliderViewAdapter

class AdsSliderAdapter(
    private val context: Context,
    private var dataset: List<Home.Advertisement>
) :
    SliderViewAdapter<AdsSliderAdapter.ViewHolder>() {


    override fun getCount(): Int {
        return dataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_image_slider, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        val data = dataset[position]
        data.image?.let {
            if (viewHolder != null) {
                viewHolder.image?.setImageResource(it)
            }
        }
    }

    class ViewHolder(itemView: View?) : SliderViewAdapter.ViewHolder(itemView) {

        var item: View? = null
        var image: ImageView? = null

        init {
            item = itemView
            image = itemView!!.findViewById(R.id.item_image_slider)
        }
    }
}