package com.example.urwood.ui.product.detail.domain

import com.example.urwood.repository.datasource.firestore.product.api.IProductRepo
import com.example.urwood.repository.model.Product
import com.example.urwood.repository.model.User
import com.example.urwood.ui.product.domain.IProduct
import com.example.urwood.utils.viewobject.Resource

class ProductDetailImpl(private val productRepository: IProductRepo) : IProductDetail {
    override suspend fun getProductDetail(productId: String): Resource<Product.ProductDetail?> =
        productRepository.getProduct(productId)

    override suspend fun getProductStore(storeId: String, ownerId: String): Resource<User.Store?> =
        productRepository.getProductStore(storeId, ownerId)

}