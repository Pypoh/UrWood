package com.example.urwood.ui.main.profile.domain

import com.example.urwood.repository.datasource.firestore.main.profile.IProfileRepo
import com.example.urwood.utils.viewobject.Resource
import com.google.firebase.auth.FirebaseAuth

class ProfileImpl(private val profileRepository: IProfileRepo) : IProfile{

}