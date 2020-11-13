package com.example.urwood.repository.model

object Home {

    data class Kategori(
        var name: String? = null,
        var image: String? = null
    )

    data class Produk(
        var image: String? = null,
        var name: String? = null,
        var price: Int? = null,
        var favorite: Boolean? = null
    )


}