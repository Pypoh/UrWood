package com.example.urwood.ui.main.profile.store.domain

import com.example.urwood.repository.datasource.firestore.store.api.IStoreRepo
import com.example.urwood.repository.model.Product
import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource


class StoreImpl(private val storeRepository: IStoreRepo) : IStore {
    override suspend fun getStoreData(storeId: String): Resource<User.Store> =
        storeRepository.getStoreData(storeId)

    override suspend fun getStoreProduct(storeId: String): Resource<List<Product.ProductDetail>?> =
        storeRepository.getStoreProduct(storeId)
}