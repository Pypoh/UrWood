package com.example.urwood.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.urwood.repository.model.User
import com.example.urwood.ui.auth.domain.IAuth
import com.example.urwood.ui.main.profile.domain.IProfile
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ProfileViewModel(
    private val useCaseProfile: IProfile,
    private val useCaseUser: IAuth
) :
    ViewModel() {

    lateinit var userResult: LiveData<Resource<User.UserData?>>
    lateinit var logoutResult: LiveData<Resource<FirebaseAuth?>>

    fun getUserInformation() {
        userResult = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val userResult = useCaseUser.getUserData()
                emit(userResult)
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

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