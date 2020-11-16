package com.example.urwood.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.urwood.repository.model.Product
import com.example.urwood.ui.main.home.domain.IHome
import com.example.urwood.ui.product.domain.IProduct
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HomeViewModel(private val useCaseHome: IHome, private val useCaseProduct: IProduct) : ViewModel() {

    lateinit var productResult: LiveData<Resource<List<Product.ProductDetail>?>>

    fun getProducts() {
        productResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCaseProduct.getProductUserList("")
                emit(data)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}