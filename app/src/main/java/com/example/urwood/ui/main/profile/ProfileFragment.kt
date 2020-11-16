package com.example.urwood.ui.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.urwood.R
import com.example.urwood.databinding.FragmentHomeBinding
import com.example.urwood.databinding.FragmentProfileBinding
import com.example.urwood.databinding.FragmentPromoBinding
import com.example.urwood.repository.datasource.auth.other.AuthRepoImpl
import com.example.urwood.repository.datasource.firestore.main.home.HomeRepoImpl
import com.example.urwood.repository.datasource.firestore.main.profile.ProfileRepoImpl
import com.example.urwood.ui.auth.AuthActivity
import com.example.urwood.ui.auth.domain.AuthImpl
import com.example.urwood.ui.main.MainActivity
import com.example.urwood.ui.main.home.HomeVMFactory
import com.example.urwood.ui.main.home.HomeViewModel
import com.example.urwood.ui.main.home.domain.HomeImpl
import com.example.urwood.ui.main.profile.domain.ProfileImpl
import com.example.urwood.utils.toast
import com.example.urwood.utils.viewobject.Resource

class ProfileFragment : Fragment() {

    private lateinit var profileDataBinding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(
            this,
            ProfileVMFactory(ProfileImpl(ProfileRepoImpl()), AuthImpl(AuthRepoImpl()))
        ).get(ProfileViewModel::class.java)
    }

    private lateinit var signOutLayout: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        profileDataBinding.profileViewModel = profileViewModel

        setupViewBinding(profileDataBinding.root)

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

    private fun intentToAuth() {
        val intent = Intent(this.context, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    private fun setupViewBinding(view: View) {
        signOutLayout = view.findViewById(R.id.layout_keluar)

    }
}