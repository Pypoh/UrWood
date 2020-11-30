package com.example.urwood.repository.datasource.firestore.store.api

import com.example.urwood.repository.model.Product
import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource

interface IStoreRepo {
    suspend fun getStoreData(storeId: String) : Resource<User.Store>
    suspend fun getStoreProduct(storeId: String) : Resource<List<Product.ProductDetail>?>
}