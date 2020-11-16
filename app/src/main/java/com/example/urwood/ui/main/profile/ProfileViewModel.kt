package com.example.urwood.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.urwood.ui.auth.domain.IAuth
import com.example.urwood.ui.main.profile.domain.IProfile
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.FirebaseAuth
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ProfileViewModel(
    private val useCaseProfile: IProfile,
    private val useCaseUser: IAuth
) :
    ViewModel() {

    lateinit var logoutResult: LiveData<Resource<FirebaseAuth?>>

    fun signOut() {
        logoutResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val logoutResult = useCaseUser.logout()
                emit(logoutResult)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}