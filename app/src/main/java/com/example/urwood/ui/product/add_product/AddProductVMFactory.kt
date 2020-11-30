package com.example.urwood.ui.product.add_product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urwood.ui.product.domain.IProduct

class AddProductVMFactory(private val useCaseProductRepo: IProduct) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IProduct::class.java).newInstance(useCaseProductRepo)
    }
}