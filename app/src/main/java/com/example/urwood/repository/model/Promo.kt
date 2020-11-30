package com.example.urwood.repository.model

import com.google.firebase.firestore.ServerTimestamp
import com.google.gson.annotations.SerializedName
import java.util.*

object Promo {

    data class Promotion(
        @SerializedName("code")
        var code: String? = null,
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("terms")
        var terms: String? = null,
        @SerializedName("startAt")
        @ServerTimestamp
        var startAt: Date? = null,
        @SerializedName("expiredAt")
        @ServerTimestamp
        var expiredAt: Date? = null
    )
}