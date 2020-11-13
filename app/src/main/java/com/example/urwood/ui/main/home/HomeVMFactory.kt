package com.example.urwood.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urwood.ui.main.home.domain.IHome

class HomeVMFactory(private val useCase: IHome) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IHome::class.java).newInstance(useCase)
    }
}