package com.example.urwood.repository.model

import android.graphics.Bitmap
import android.net.Uri

object Image {

    data class ImagePhoto(
        var uri: Uri? = null,
        var type: Int? = null
    )
}