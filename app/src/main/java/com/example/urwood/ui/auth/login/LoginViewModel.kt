package com.example.urwood.ui.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.urwood.repository.model.User
import com.example.urwood.ui.auth.login.domain.ILogin
import com.example.urwood.utils.CustomException
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val useCase: ILogin) : ViewModel() {

    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    lateinit var result: LiveData<Resource<AuthResult?>>

    fun loginWithEmailAndPassword() {
        result = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                if (email.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
                    emit(Resource.Failure(CustomException("Email or Password can't be blank")))
                } else {
                    val loginAuthResult: Resource<AuthResult?> = useCase.loginWithEmailAndPassword(
                        email = email.value!!,
                        password = password.value!!
                    )
                    emit(loginAuthResult)
                }
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }
}