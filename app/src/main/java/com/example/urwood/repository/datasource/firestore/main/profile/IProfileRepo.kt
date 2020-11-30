package com.example.urwood.repository.datasource.firestore.main.profile

import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource

interface IProfileRepo {
    suspend fun updateUserData(userData: User.UserData) : Resource<Int>
    suspend fun updateStoreData(storeData: User.Store) : Resource<Int>
}