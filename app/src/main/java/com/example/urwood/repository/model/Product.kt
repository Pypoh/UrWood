package com.example.urwood.repository.model

import com.google.firebase.firestore.Exclude
import com.google.gson.annotations.SerializedName

object Product {

    data class ProductDetail(
        @SerializedName("image")
        var image: String? = null,
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("price")
        var price: Int? = null,
        @SerializedName("quantity")
        var quantity: Int? = null,
        @SerializedName("category")
        var category: String? = null,
        @SerializedName("description")
        var description: String? = null,
        @SerializedName("storeId")
        var storeId: String? = null,
        @SerializedName("rating")
        var rating: Int? = null,
        @SerializedName("sold")
        var sold: Int? = null,
        @SerializedName("variant")
        var variant: ArrayList<String> = ArrayList(),
        @Exclude
        var favorite: Boolean? = null,
        @Exclude
        var productId: String? = null,
    ) {
        constructor(image: String?, name: String?, price: Int?, favorite: Boolean?, productId: String?) : this(
            image,
            name,
            price,
            null,
            null,
            null,
            null,
            null,
            null,
            ArrayList(),
            favorite,
            productId
        )
    }
}