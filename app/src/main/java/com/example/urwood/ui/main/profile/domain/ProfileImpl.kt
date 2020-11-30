package com.example.urwood.ui.main.profile.domain

import com.example.urwood.repository.datasource.firestore.main.profile.IProfileRepo
import com.example.urwood.repository.model.User
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.FirebaseAuth

class ProfileImpl(private val profileRepository: IProfileRepo) : IProfile {
    override suspend fun updateUserData(userData: User.UserData): Resource<Int> =
        profileRepository.updateUserData(userData)

    override suspend fun updateStoreData(storeData: User.Store): Resource<Int> =
        profileRepository.updateStoreData(storeData)

}