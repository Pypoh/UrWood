package com.example.urwood.ui.main.profile.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urwood.ui.main.profile.domain.IProfile

class ProfileEditVMFactory(private val useCaseProfile: IProfile) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IProfile::class.java).newInstance(useCaseProfile)
    }
}