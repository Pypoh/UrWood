package com.example.urwood.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.urwood.ui.auth.signup.domain.ISignup
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.AuthResult
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class SignupViewModel(private val useCase: ISignup) : ViewModel() {

    var email: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()
    var confPassword: MutableLiveData<String> = MutableLiveData()

    lateinit var result: LiveData<Resource<AuthResult?>>

    fun registerWithEmailAndPassword() {
        result = liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val signupAuthResult: Resource<AuthResult?> = useCase.registerWithEmailAndPassword(
                    email = email.value!!,
                    password = password.value!!
                )
                emit(signupAuthResult)
            } catch (e: Exception) {
                emit(Resource.Failure(e.cause!!))
            }
        }
    }
}