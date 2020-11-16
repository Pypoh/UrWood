package com.example.urwood.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urwood.ui.auth.domain.IAuth
import com.example.urwood.ui.main.home.domain.IHome
import com.example.urwood.ui.main.profile.domain.IProfile

class ProfileVMFactory(private val useCaseProfile: IProfile, private val useCaseUser: IAuth) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IProfile::class.java, IAuth::class.java).newInstance(useCaseProfile, useCaseUser)
    }
}