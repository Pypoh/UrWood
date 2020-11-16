package com.example.urwood.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urwood.ui.main.home.domain.IHome
import com.example.urwood.ui.product.domain.IProduct

class HomeVMFactory(private val useCaseHome: IHome, private val useCaseProductRepo: IProduct) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IHome::class.java, IProduct::class.java).newInstance(useCaseHome, useCaseProductRepo)
    }
}