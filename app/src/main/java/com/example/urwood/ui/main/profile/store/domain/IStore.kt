package com.example.urwood.ui.main.profile.store.domain

import com.example.urwood.repository.model.Product
import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource

interface IStore {
    suspend fun getStoreData(storeId: String) : Resource<User.Store>
    suspend fun getStoreProduct(storeId: String) : Resource<List<Product.ProductDetail>?>
}