package com.example.urwood.ui.product.detail.domain

import com.example.urwood.repository.model.Product
import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource

interface IProductDetail {
    suspend fun getProductDetail(productId: String): Resource<Product.ProductDetail?>

    suspend fun getProductStore(storeId: String, ownerId: String): Resource<User.Store?>
}