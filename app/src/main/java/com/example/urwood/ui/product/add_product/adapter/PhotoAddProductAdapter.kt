package com.example.urwood.ui.product.add_product.adapter

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.urwood.R
import com.example.urwood.repository.model.Image
import com.example.urwood.repository.model.Product
import com.example.urwood.ui.main.home.adapter.ProdukAdapter
import com.google.android.material.button.MaterialButton

class PhotoAddProductAdapter(
    private val context: Context,
    private var dataset: ArrayList<Image.ImagePhoto>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(photoDetailModel: Image.ImagePhoto)
    }

    override fun getItemViewType(position: Int): Int {

        return dataset[position].type!!
    }


    override fun getItemCount(): Int {
        return dataset.size
    }

    fun addImageItem(data: Image.ImagePhoto) {
        dataset.add(data)
        this.notifyDataSetChanged()
    }

    fun getItemUri(position: Int) : Uri? {
        return dataset[position].uri
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addPhotoButton: MaterialButton = itemView.findViewById(R.id.button_add_photo)
        val buttonLayout: ConstraintLayout =
            itemView.findViewById(R.id.layout_image_button_add_product)

        fun bind(model: Image.ImagePhoto, listener: OnItemClickListener) {
            addPhotoButton.setOnClickListener {
                listener.onItemClick(model)
            }
        }
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_image_add_product)
        val itemLayout: ConstraintLayout = itemView.findViewById(R.id.item_image_layout_add_product)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data: Image.ImagePhoto = dataset[position]
        when (holder) {
            is ButtonViewHolder -> {
                if (dataset.size > 1) {
                    val height = ConstraintLayout.LayoutParams.MATCH_PARENT
                    val width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                    val layoutParams = ConstraintLayout.LayoutParams(width, height)
                    holder.buttonLayout.layoutParams = layoutParams
                    holder.addPhotoButton.isEnabled = false
                }
                holder.bind(data, onItemClickListener)
            }
            is ImageViewHolder -> {
                val height = ConstraintLayout.LayoutParams.MATCH_PARENT
                val width = ConstraintLayout.LayoutParams.WRAP_CONTENT
                val layoutParams = ConstraintLayout.LayoutParams(width, height)
                layoutParams.marginEnd = 32
                holder.itemImage.layoutParams = layoutParams
                holder.itemLayout.layoutParams = layoutParams

                Glide
                    .with(context)
                    .load(data.uri)
                    .into(holder.itemImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null

        return when (viewType) {
            0 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image_button_placeholder, parent, false)
                ButtonViewHolder(view)
            }

            else -> {
                view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
                ImageViewHolder(view)
            }
        }
    }


}