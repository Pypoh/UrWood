package com.example.urwood.ui.main.promo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.urwood.R
import com.example.urwood.repository.model.Promo
import com.example.urwood.ui.main.home.adapter.ProdukAdapter
import com.example.urwood.utils.helper.Parser
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat

class PromoAdapter(val context: Context, var dataSet: List<Promo.Promotion>) :
    RecyclerView.Adapter<PromoAdapter.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(promoModel: Promo.Promotion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_promo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPromo: Promo.Promotion = dataSet[position]
        holder.bind(dataPromo, onItemClickListener)
        holder.titleTextPromo.text = dataPromo.name
        holder.expirationTextPromo.text =
            "Berlaku sampai ${Parser.simpleDateFormatFromDate(dataPromo.expiredAt!!)}"

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setPromoData(data: List<Promo.Promotion>) {
        this.dataSet = data
    }

    fun getOnItemClickListener(): OnItemClickListener {
        return onItemClickListener
    }

    public fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextPromo: MaterialTextView = itemView.findViewById(R.id.text_title_promo)
        var expirationTextPromo: MaterialTextView =
            itemView.findViewById(R.id.text_expiration_promo)

        var layoutPromo: LinearLayout = itemView.findViewById(R.id.layout_promo)

        fun bind(model: Promo.Promotion, listener: OnItemClickListener) {
            layoutPromo.setOnClickListener {
                listener.onItemClick(model)
            }
        }
    }

}