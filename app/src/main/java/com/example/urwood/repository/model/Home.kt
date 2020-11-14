package com.example.urwood.repository.model

object Home {

    data class CircleIcon(
        var name: String? = null,
        var image: Int? = null,
        var type: Int // 0 Kategori, 1 Toko
    )

    data class Produk(
        var image: String? = null,
        var name: String? = null,
        var price: Int? = null,
        var favorite: Boolean? = null
    )

    data class Advertisement(
        var image: Int? = null
    )


}