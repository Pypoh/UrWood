package com.example.urwood.repository.model

import com.google.firebase.firestore.Exclude
import com.google.gson.annotations.SerializedName

object User {

    data class UserData(
        var image: String? = null,
        var email: String? = null,
        var name: String? = null,
        var phoneNumber: String? = null,
        var storeId: String? = null,
        var chatGroup: ArrayList<String>? = null,
        @Exclude
        var userId: String? = null
    ) {
        constructor(email: String, storeId: String?) : this(null, email, null, null, storeId, ArrayList(), null)
        constructor(image: String, email: String, name: String, phoneNumber: String?) : this(
            image,
            email,
            name,
            phoneNumber,
            null,
            ArrayList(),
            null
        )
    }

    data class Store(
        @SerializedName("storeLocation")
        var storeLocation: String? = null,
        @SerializedName("storeName")
        var storeName: String? = null,
        @SerializedName("storeImage")
        var storeImage: String? = null,
        @SerializedName("storeDescription")
        var storeDescription: String? = null,
        @Exclude
        var storeId: String? = null,
        @Exclude
        var storePhoneNumber: String? = null,
        @Exclude
        var loggedInUserStore: Boolean = false
    )

}