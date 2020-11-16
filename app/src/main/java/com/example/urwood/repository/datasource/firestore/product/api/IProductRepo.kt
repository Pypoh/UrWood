package com.example.urwood.repository.datasource.firestore.product.api

import com.example.urwood.repository.model.Product
import com.example.urwood.utils.viewobject.Resource

interface IProductRepo {
    suspend fun getProduct(productId: String): Resource<Product.ProductDetail?>

    suspend fun getProductUserList(uid: String) : Resource<List<Product.ProductDetail>?>

    suspend fun getAllProducts() : Resource<List<Product.ProductDetail>?>

    suspend fun postProduct(product: Product.ProductDetail)

}