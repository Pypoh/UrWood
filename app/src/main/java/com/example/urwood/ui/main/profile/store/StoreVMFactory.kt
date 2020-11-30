package com.example.urwood.ui.main.profile.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urwood.ui.main.home.domain.IHome
import com.example.urwood.ui.main.profile.store.domain.IStore
import com.example.urwood.ui.product.domain.IProduct

class StoreVMFactory(private val useCaseHome: IStore, private val useCaseProductRepo: IProduct) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IStore::class.java, IProduct::class.java).newInstance(useCaseHome, useCaseProductRepo)
    }
}