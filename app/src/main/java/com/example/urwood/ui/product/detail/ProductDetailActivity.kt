package com.example.urwood.ui.product.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.urwood.R
import com.example.urwood.repository.model.Product
import com.google.gson.Gson

class ProductDetailActivity : AppCompatActivity() {

    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val objectString = intent.getStringExtra("product")
        val produkModel = gson.fromJson(objectString, Product.ProductDetail::class.java)



    }
}