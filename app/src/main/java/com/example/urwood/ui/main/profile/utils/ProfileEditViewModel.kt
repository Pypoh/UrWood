package com.example.urwood.ui.main.profile.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.urwood.repository.model.User
import com.example.urwood.ui.main.profile.domain.IProfile
import com.example.urwood.utils.viewobject.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ProfileEditViewModel(private val useCaseProfile: IProfile) : ViewModel() {

    lateinit var userUpdateResult: LiveData<Resource<Int>>
    lateinit var storeUpdateResult: LiveData<Resource<Int>>

    fun updateUserData(userData: User.UserData) {
        userUpdateResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val data = useCaseProfile.updateUserData(userData)
                emit(data)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    fun updateStoreData(storeData: User.Store) {
        storeUpdateResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                Log.d("StoreDebug", storeData.toString())
                val data = useCaseProfile.updateStoreData(storeData)
                emit(data)
            } catch (e: Exception) {
                Log.d("StoreDebug", "Error: $e")
                emit(Resource.Failure(e))
            }
        }
    }

}