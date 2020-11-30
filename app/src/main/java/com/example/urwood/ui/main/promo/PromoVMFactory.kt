package com.example.urwood.ui.main.promo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urwood.ui.main.promo.domain.IPromo

class PromoVMFactory(private val useCase: IPromo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IPromo::class.java).newInstance(useCase)
    }
}