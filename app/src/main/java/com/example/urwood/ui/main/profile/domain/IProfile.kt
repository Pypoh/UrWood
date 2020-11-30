package com.example.urwood.ui.main.profile.domain

import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.FirebaseAuth

interface IProfile {
    suspend fun updateUserData(userData: User.UserData) : Resource<Int>
    suspend fun updateStoreData(storeData: User.Store) : Resource<Int>

}