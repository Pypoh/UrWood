package com.example.urwood.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.urwood.ui.main.MainActivity
import com.example.urwood.R
import com.example.urwood.databinding.FragmentLoginBinding
import com.example.urwood.repository.datasource.auth.login.LoginRepoImpl
import com.example.urwood.ui.auth.login.domain.LoginImpl
import com.example.urwood.utils.toast
import com.example.urwood.utils.viewobject.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class LoginFragment : Fragment() {

    // Utils
    private lateinit var alertDialog: AlertDialog

    private lateinit var loginDataBinding: FragmentLoginBinding

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            LoginVMFactory(LoginImpl(LoginRepoImpl()))
        ).get(LoginViewModel::class.java)
    }

    // Views
    private lateinit var loginButton: MaterialButton
    private lateinit var toSignupButton: MaterialTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Setup Data Binding
        loginDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        loginDataBinding.loginViewModel = loginViewModel

        // View Binding
        setupViewBinding(loginDataBinding.root)

        // Init Progress Dialog
        initProgressDialog()

        // Setup Login Button onClick Listener
        setupButtonListener()

        return loginDataBinding.root
    }

    private fun moveToSignup() {
        requireView().findNavController().navigate(R.id.navigation_signup)
    }

    private fun setupViewBinding(view: View) {
        loginButton = view.findViewById(R.id.btn_login_login)
        toSignupButton = view.findViewById(R.id.text_to_signup)
    }

    private fun setupButtonListener() {
        loginButton.setOnClickListener {
            loginViewModel.loginWithEmailAndPassword()
            loginViewModel.result.observe(viewLifecycleOwner, Observer { task ->
                when (task) {
                    is Resource.Loading -> {
                        if (!alertDialog.isShowing) alertDialog.show()
                    }

                    is Resource.Success -> {
                        requireContext().toast("Success")
                        if (alertDialog.isShowing) alertDialog.dismiss()
                        intentToMain()
                    }

                    is Resource.Failure -> {
                        if (alertDialog.isShowing) alertDialog.dismiss()
                        requireContext().toast(task.throwable.message.toString())
                    }

                    else -> {
                        // do nothing
                        requireContext().toast(task.toString())
                    }
                }
            })
        }

        toSignupButton.setOnClickListener {
            moveToSignup()
        }
    }

    private fun intentToMain() {
        val intent = Intent(this.context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    private fun initProgressDialog() {
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.progress_dialog, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        alertDialog = dialogBuilder.create()
    }
}