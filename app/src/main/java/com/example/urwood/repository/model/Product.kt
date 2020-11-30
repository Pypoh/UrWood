package com.example.urwood.repository.model

import android.net.Uri
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
        var rating: Int? = 0,
        @SerializedName("sold")
        var sold: Int? = 0,
        @SerializedName("variant")
        var variant: ArrayList<String> = ArrayList(),
        @SerializedName("ownerId")
        var ownerId: String? = null,
        @Exclude
        var favorite: Boolean? = null,
        @Exclude
        var productId: String? = null,
        @Exclude
        var imageUri: Uri? = null
    ) {
        constructor(image: String?, name: String?, price: Int?, favorite: Boolean?, productId: String?) : this(
            image,
            name,
            price,
            null,
            null,
            null,
            null,
            0,
            0,
            ArrayList(),
            null,
            favorite,
            productId,
        )

        constructor(imageUri: Uri?, name: String?, category: String?, variant: ArrayList<String>, description: String?, price: Int?) : this(
            null,
            name,
            price,
            null,
            category,
            description,
            null,
            0,
            0,
            variant,
            null,
            null,
            null,
            imageUri
        )
    }


}