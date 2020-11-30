package com.example.urwood.ui.product.add_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.urwood.repository.model.Product
import com.example.urwood.ui.product.domain.IProduct
import com.example.urwood.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class AddProductViewModel(private val useCase: IProduct) : ViewModel() {

    lateinit var categoryResult: ArrayList<String>

    lateinit var postResult: LiveData<Resource<Int>>

    fun postProduct(productData: Product.ProductDetail) {
        postResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val postResult: Resource<Int> = useCase.postProduct(productData)
                emit(postResult)
            } catch (e: Exception) {

            }
        }
    }

}