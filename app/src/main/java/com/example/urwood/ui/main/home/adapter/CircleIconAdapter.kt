package com.example.urwood.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.example.urwood.R
import com.example.urwood.repository.model.Home
import com.google.android.material.textview.MaterialTextView
import de.hdodenhof.circleimageview.CircleImageView

class CircleIconAdapter(
    private val context: Context,
    private var dataset: List<Home.CircleIcon>
) :
    RecyclerView.Adapter<CircleIconAdapter.ViewHolder>() {

    private lateinit var onItemClickListener: AdapterView.OnItemClickListener


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_circle_icon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataCircleIcon: Home.CircleIcon = dataset[position]
        holder.textKategori.text = dataCircleIcon.name.toString()

        when (dataCircleIcon.type) {
            0 -> {
                dataCircleIcon.image?.let { holder.iconCircleIcon.setImageResource(it as Int) }
            }
            1 -> {
                holder.imageKategori.visibility = View.VISIBLE
                if (dataCircleIcon.image != null) {
                    holder.imageKategori.setImageResource(dataCircleIcon.image!! as Int)
                    holder.iconCircleIcon.visibility = View.GONE
                } else {
                    val formattedName = dataCircleIcon.name?.let { formatProfileName(it) }
                    holder.textNoImage.text = formattedName
                    holder.textKategori
                }
            }
        }
    }

    private fun formatProfileName(name: String): String {
        var formattedName = ""
        val stringArray = name.split(" ").toTypedArray()
        for (string in stringArray) {
            formattedName += string.first()
        }
        return formattedName
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageKategori: CircleImageView = itemView.findViewById(R.id.item_image_circle_icon)
        var iconCircleIcon: ImageView = itemView.findViewById(R.id.item_icon_image_circle_icon)
        var textKategori: MaterialTextView = itemView.findViewById(R.id.item_text_circle_icon)
        var textNoImage: MaterialTextView =
            itemView.findViewById(R.id.item_text_no_image_circle_icon)

        fun bind() {
            // onclick listener
        }

    }

}