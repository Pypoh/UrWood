package com.example.urwood.ui.main.profile.utils

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.urwood.R
import com.example.urwood.databinding.ActivityProfileEditBinding
import com.example.urwood.repository.datasource.firestore.main.profile.ProfileRepoImpl
import com.example.urwood.repository.model.User
import com.example.urwood.ui.main.profile.domain.ProfileImpl
import com.example.urwood.utils.toast
import com.example.urwood.utils.viewobject.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView

class ProfileEditActivity : AppCompatActivity() {

    private lateinit var profileEditDataBinding: ActivityProfileEditBinding

    private val profileEditViewModel: ProfileEditViewModel by lazy {
        ViewModelProvider(
            this,
            ProfileEditVMFactory(ProfileImpl(ProfileRepoImpl()))
        ).get(ProfileEditViewModel::class.java)
    }

    private val gson: Gson = Gson()

    private lateinit var backImageButton: ImageView
    private lateinit var imageProfile: CircleImageView
    private lateinit var nameProfile: TextInputEditText
    private lateinit var emailProfile: TextInputEditText
    private lateinit var phoneProfile: TextInputEditText
    private lateinit var saveButton: MaterialButton

    private lateinit var headerDescText: MaterialTextView
    private lateinit var inputDescText: TextInputEditText

    private lateinit var emailLayout: RelativeLayout
    private lateinit var phoneLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        val userObject = intent.getStringExtra("userData")
        val storeObject = intent.getStringExtra("storeData")
        val userData = gson.fromJson(userObject, User.UserData::class.java)
        val storeData = gson.fromJson(storeObject, User.Store::class.java)

        profileEditDataBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_profile_edit)
        profileEditDataBinding.profileEditViewModel = profileEditViewModel

        setupViews(profileEditDataBinding.root)

        if (userData != null) {
            setViewData(userData)
        } else {
            setViewData(storeData)
        }
    }

    private fun setViewData(data: Any) {
        when (data) {
            is User.UserData -> {
                emailLayout.visibility = View.VISIBLE
                phoneLayout.visibility = View.VISIBLE
//                Glide
//                    .with(this)
//                    .load(data.image)
//                    .centerCrop()
//                    .placeholder(R.drawable.ikea_logo)
//                    .into(imageProfile)

                nameProfile.setText(data.name.toString())
                emailProfile.setText(data.email.toString())
                phoneProfile.setText(data.phoneNumber.toString())

                saveButton.setOnClickListener {
                    val updatedUserData = User.UserData(
                        null,
                        emailProfile.text.toString(),
                        nameProfile.text.toString(),
                        phoneProfile.text.toString()
                    )
                    profileEditViewModel.updateUserData(updatedUserData)
                    profileEditViewModel.userUpdateResult.observe(this, { task ->
                        when (task) {
                            is Resource.Loading -> {
                            }

                            is Resource.Success -> {
                                setIntentResult(task.data, updatedUserData)
                            }

                            is Resource.Failure -> {
                                toast(task.throwable.message.toString())
                            }

                            else -> {
                                toast(task.toString())
                            }
                        }
                    })
                }
            }

            is User.Store -> {
                headerDescText.visibility = View.VISIBLE
                inputDescText.visibility = View.VISIBLE
//                Glide
//                    .with(this)
//                    .load(data.storeImage)
//                    .centerCrop()
//                    .placeholder(R.drawable.ikea_logo)
//                    .into(imageProfile)

                nameProfile.setText(data.storeName.toString())
                inputDescText.setText(data.storeDescription.toString())

                saveButton.setOnClickListener {
                    val updatedStoreData = User.Store(
                        null,
                        nameProfile.text.toString(),
                        null,
                        inputDescText.text.toString()
                    )
                    updatedStoreData.storeId = data.storeId
                    updatedStoreData.loggedInUserStore = data.loggedInUserStore
                    profileEditViewModel.updateStoreData(updatedStoreData)
                    profileEditViewModel.storeUpdateResult.observe(this, { task ->
                        when (task) {
                            is Resource.Loading -> {
                            }

                            is Resource.Success -> {
                                setIntentResult(task.data, updatedStoreData)
                            }

                            is Resource.Failure -> {
                            }

                            else -> {
                                toast(task.toString())
                            }
                        }
                    })
                }


            }
        }
    }

    private fun setIntentResult(code: Int, data: Any) {
        val intent = Intent()
        when (data) {
            is User.UserData -> {
                intent.putExtra("userData", gson.toJson(data))
            }
            is User.Store -> {
                intent.putExtra("storeData", gson.toJson(data))
            }
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun setupViews(view: View) {
        backImageButton = view.findViewById(R.id.back_arrow_edit_profile)
        backImageButton.setOnClickListener {
            onBackPressed()
        }
        imageProfile = view.findViewById(R.id.profile_picture_edit_profile)
        nameProfile = view.findViewById(R.id.iet_name_edit_profile)
        emailProfile = view.findViewById(R.id.iet_email_edit_profile)
        phoneProfile = view.findViewById(R.id.iet_phone_edit_profile)
        saveButton = view.findViewById(R.id.save_edit_profile)

        headerDescText = view.findViewById(R.id.text_header_desc_profile_edit)
        inputDescText = view.findViewById(R.id.iet_desc_edit_profile)

        emailLayout = view.findViewById(R.id.layout_email_edit_profile)
        phoneLayout = view.findViewById(R.id.layout_phone_edit_profile)
    }
}