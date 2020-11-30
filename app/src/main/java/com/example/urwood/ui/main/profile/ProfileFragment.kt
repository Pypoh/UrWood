package com.example.urwood.ui.main.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.urwood.R
import com.example.urwood.databinding.FragmentProfileBinding
import com.example.urwood.repository.datasource.auth.other.AuthRepoImpl
import com.example.urwood.repository.datasource.firestore.main.profile.ProfileRepoImpl
import com.example.urwood.repository.model.User
import com.example.urwood.ui.auth.AuthActivity
import com.example.urwood.ui.auth.domain.AuthImpl
import com.example.urwood.ui.main.profile.domain.ProfileImpl
import com.example.urwood.ui.main.profile.store.StoreActivity
import com.example.urwood.ui.main.profile.utils.ProfileEditActivity
import com.example.urwood.utils.helper.Parser
import com.example.urwood.utils.toast
import com.example.urwood.utils.viewobject.Resource
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

class ProfileFragment : Fragment() {

    private lateinit var profileDataBinding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(
            this,
            ProfileVMFactory(ProfileImpl(ProfileRepoImpl()), AuthImpl(AuthRepoImpl()))
        ).get(ProfileViewModel::class.java)
    }

    private val gson: Gson = Gson()

    private lateinit var rimagePictureProfile: CircleImageView
    private lateinit var backgroundImagePictureProfile: ImageView
    private lateinit var textPictureProfile: MaterialTextView
    private lateinit var namaProfileText: MaterialTextView
    private lateinit var editProfileImage: ImageView
    private lateinit var editProfileLayout: ConstraintLayout

    private lateinit var layoutToko: RelativeLayout

    private lateinit var signOutLayout: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        profileDataBinding.profileViewModel = profileViewModel

        setupViewBinding(profileDataBinding.root)

        // Fetch User Data
        fetchCurrentUserData()

        // Sign Out
        signOutLayout.setOnClickListener {
            profileViewModel.signOut()
            profileViewModel.logoutResult.observe(viewLifecycleOwner, Observer { task ->
                when (task) {
                    is Resource.Loading -> {
                        // TODO: Loading
                    }

                    is Resource.Success -> {
                        requireContext().toast("Logout Success")
                        intentToAuth()
                    }

                    is Resource.Failure -> {
                        requireContext().toast(task.throwable.message.toString())
                    }

                    else -> {
                        // do nothing
                        requireContext().toast(task.toString())
                    }
                }
            })
        }

        return profileDataBinding.root
    }

    private fun fetchCurrentUserData() {
        profileViewModel.getUserInformation()
        profileViewModel.userResult.observe(viewLifecycleOwner, { task ->
            when (task) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    updateUI(task.data!!)
                }

                is Resource.Failure -> {
                    requireContext().toast(task.throwable.message.toString())
                }

                else -> {
                    // do nothing
                    requireContext().toast(task.toString())
                }
            }
        })
    }

    private fun updateUI(userData: User.UserData) {
        try {
            namaProfileText.text = userData.name

            if (!userData.image.isNullOrBlank()) {
                Glide
                    .with(requireContext())
                    .load(userData.image)
                    .centerCrop()
                    .placeholder(R.drawable.ikea_logo)
                    .into(rimagePictureProfile)
                backgroundImagePictureProfile.visibility = View.GONE
            } else {
                val formattedName = userData.name?.let {
                    Parser.formatProfileName(it)
                }
                textPictureProfile.text = formattedName
            }

            editProfileLayout.setOnClickListener {
                intentToEdit(userData)
            }
        } catch (e: Exception) {
        }

    }

    private fun intentToAuth() {
        val intent = Intent(this.context, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    private fun intentToEdit(userData: User.UserData) {
        val intent = Intent(this.context, ProfileEditActivity::class.java)
        intent.putExtra("userData", gson.toJson(userData))
        startActivityForResult(intent, 101)
    }

    private fun intentToStore() {
        when (profileViewModel.userResult.value) {
            is Resource.Loading -> {
            }

            is Resource.Success -> {
                val data = (profileViewModel.userResult.value as Resource.Success<User.UserData?>).data
                val intent = Intent(this.context, StoreActivity::class.java)
                intent.putExtra("storeId", data!!.storeId)
                startActivity(intent)
            }

            is Resource.Failure -> {
            }

            else -> {
                // do nothing
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val userObject = data!!.getStringExtra("userData")
                val userData = gson.fromJson(userObject, User.UserData::class.java)
                Log.d("ProfileDebug", "${userData.name}")

                updateUI(userData)
            }
        }
    }

    private fun setupViewBinding(view: View) {
        namaProfileText = view.findViewById(R.id.text_nama_profile)
        signOutLayout = view.findViewById(R.id.layout_keluar)
        rimagePictureProfile = view.findViewById(R.id.rimg_profile_picture)
        backgroundImagePictureProfile = view.findViewById(R.id.item_icon_image_profile_picture)
        textPictureProfile = view.findViewById(R.id.item_text_no_image_profile_picture)
        editProfileLayout = view.findViewById(R.id.layout_header_profile)

        layoutToko = view.findViewById(R.id.layout_toko_profile)
        layoutToko.setOnClickListener {
            intentToStore()
        }

    }
}