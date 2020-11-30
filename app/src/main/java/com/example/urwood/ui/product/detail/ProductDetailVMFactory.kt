package com.example.urwood.ui.product.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urwood.ui.chat.domain.IChat
import com.example.urwood.ui.main.home.domain.IHome
import com.example.urwood.ui.product.detail.domain.IProductDetail
import com.example.urwood.ui.product.domain.IProduct

class ProductDetailVMFactory(private val useCaseProductRepo: IProductDetail, private val useCaseChat: IChat) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IProductDetail::class.java, IChat::class.java).newInstance(useCaseProductRepo, useCaseChat)
    }
}