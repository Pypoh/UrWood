package com.example.urwood.repository.model

object Home {

    data class CircleIcon(
        var name: String? = null,
        var image: Any? = null,
        var type: Int // 0 Kategori, 1 Toko
    )

    data class Advertisement(
        var image: Int? = null
    )


}