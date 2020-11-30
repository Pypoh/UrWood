package com.example.urwood.ui.product.domain

import com.example.urwood.repository.model.Product
import com.example.urwood.utils.viewobject.Resource

interface IProduct {
    suspend fun getProduct(productId: String): Resource<Product.ProductDetail?>

    suspend fun getProductUserList(uid: String) : Resource<List<Product.ProductDetail>?>

    suspend fun getAllProducts() : Resource<List<Product.ProductDetail>?>

    suspend fun postProduct(product: Product.ProductDetail) : Resource<Int>
}