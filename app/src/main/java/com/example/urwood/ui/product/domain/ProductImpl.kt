package com.example.urwood.ui.product.domain

import com.example.urwood.repository.datasource.firestore.product.api.IProductRepo
import com.example.urwood.repository.model.Product
import com.example.urwood.utils.viewobject.Resource

class ProductImpl(private val productRepository: IProductRepo) : IProduct {
    override suspend fun getProduct(productId: String): Resource<Product.ProductDetail?> =
        productRepository.getProduct(productId)

    override suspend fun getProductUserList(uid: String): Resource<List<Product.ProductDetail>?> =
        productRepository.getProductUserList(uid)

    override suspend fun getAllProducts(): Resource<List<Product.ProductDetail>?> =
        productRepository.getAllProducts()

    override suspend fun postProduct(product: Product.ProductDetail) =
        productRepository.postProduct(product)
}