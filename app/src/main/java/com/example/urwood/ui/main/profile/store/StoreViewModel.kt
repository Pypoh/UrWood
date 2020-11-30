package com.example.urwood.ui.main.profile.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.urwood.repository.model.Product
import com.example.urwood.repository.model.User
import com.example.urwood.ui.main.home.domain.IHome
import com.example.urwood.ui.main.profile.store.domain.IStore
import com.example.urwood.ui.product.domain.IProduct
import com.example.urwood.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class StoreViewModel(private val useCaseStore: IStore, private val useCaseProduct: IProduct) : ViewModel() {

    lateinit var storeResult: LiveData<Resource<User.Store>>
    lateinit var storeProductsResult: LiveData<Resource<List<Product.ProductDetail>?>>

    fun getStoreData(storeId: String) {
        storeResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCaseStore.getStoreData(storeId)
                emit(data)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun getStoreProducts(storeId: String) {
        storeProductsResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCaseStore.getStoreProduct(storeId)
                emit(data)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

}