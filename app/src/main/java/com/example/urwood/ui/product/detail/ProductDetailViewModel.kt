package com.example.urwood.ui.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.urwood.repository.model.Chat
import com.example.urwood.repository.model.Product
import com.example.urwood.repository.model.User
import com.example.urwood.ui.chat.domain.IChat
import com.example.urwood.ui.product.detail.domain.IProductDetail
import com.example.urwood.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ProductDetailViewModel(
    private val useCaseProduct: IProductDetail,
    private val useCaseChat: IChat
) : ViewModel() {

    lateinit var productResult: LiveData<Resource<Product.ProductDetail?>>

    lateinit var storeResult: LiveData<Resource<User.Store?>>

    lateinit var chatGroupResult: LiveData<Resource<String>>

    fun getProductDetailData(productId: String) {
        productResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCaseProduct.getProductDetail(productId)
                emit(data)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun getStoreData(storeId: String, ownerId: String) {
        storeResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCaseProduct.getProductStore(storeId, ownerId)
                emit(data)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

//    fun sendMessage(message: Chat.Message?, sendTo: String) {
//        chatGroupResult = liveData(Dispatchers.IO) {
//            emit(Resource.Loading())
//            try {
//                val data = useCaseChat.sendMessage(message, sendTo, null)
//                emit(data)
//            } catch (e: Exception) {
//                emit(Resource.Failure(e))
//            }
//        }
//    }

    fun createGroup(targetPerson: String) {
        chatGroupResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCaseChat.createGroup(targetPerson)
                emit(data)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }




}