package com.example.urwood.ui.auth.signup

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
import com.example.urwood.databinding.FragmentSignupBinding
import com.example.urwood.repository.datasource.auth.signup.SignupRepoImpl
import com.example.urwood.ui.auth.signup.domain.SignupImpl
import com.example.urwood.utils.toast
import com.example.urwood.utils.viewobject.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class SignupFragment : Fragment() {

    private lateinit var alertDialog: AlertDialog

    private lateinit var signupDataBinding: FragmentSignupBinding

    private val signupViewModel: SignupViewModel by lazy {
        ViewModelProvider(
            this,
            SignupVMFactory(SignupImpl(SignupRepoImpl()))
        ).get(SignupViewModel::class.java)
    }

    private lateinit var signupButton: MaterialButton
    private lateinit var toLoginButton: MaterialTextView
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var passwordConfInput: TextInputEditText

    private var status: Boolean? = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Data Binding
        signupDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        signupDataBinding.signupViewModel = signupViewModel

        // View Binding
        setupViewBinding(signupDataBinding.root)

        // Progress Dialog
        initProgressDialog()

        // Event Listener
        setupButtonListener()

        // Inflate the layout for this fragment
        return signupDataBinding.root
    }

    private fun setupButtonListener() {
        signupButton.setOnClickListener {
            if (setInputNull() == true) {
                handleSignup()
            } else {
                setInputObserver()
            }
        }

        toLoginButton.setOnClickListener {
            moveToLogin()
        }
    }

    private fun intentToMain() {
        val intent = Intent(this.context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    private fun moveToLogin() {
        requireView().findNavController().navigate(R.id.navigation_login)
    }

    private fun setInputObserver() {
        if (status == false) {
            return
        }

        // Set Observer
        signupViewModel.email.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                emailInput.error = "Email tidak boleh kosong"
            } else {
                emailInput.error = null
            }
        })

        signupViewModel.password.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                passwordInput.error = "Password tidak boleh kosong"
            } else {
                passwordInput.error = null
            }
        })

        signupViewModel.confPassword.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                passwordConfInput.error = "Konfirmasi Password tidak boleh kosong"
            } else {
                passwordConfInput.error = null
            }
        })
    }

    private fun handleSignup() {
        // Handle Register
        signupViewModel.registerWithEmailAndPassword()
        signupViewModel.result.observe(viewLifecycleOwner, Observer { task ->
            when (task) {
                is Resource.Loading -> {
                    if (!alertDialog.isShowing) alertDialog.show()
                }

                is Resource.Success -> {
                    // TODO: Intent ke main
                    requireContext().toast("Success")
                    if (alertDialog.isShowing) alertDialog.dismiss()
//                    signupViewModel.insertDataToDatabase()
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

    private fun setupViewBinding(root: View) {
        signupButton = root.findViewById(R.id.btn_signup_signup)
        toLoginButton = root.findViewById(R.id.text_to_login)
        emailInput = root.findViewById(R.id.iet_email_signup)
        passwordInput = root.findViewById(R.id.iet_pass_signup)
        passwordConfInput = root.findViewById(R.id.iet_pass_conf_signup)
    }

    private fun setInputNull(): Boolean? {
        // Init new state of status
        status = true

        if (signupViewModel.email.value.isNullOrEmpty()) {
            emailInput.error = "Email tidak boleh kosong"
            status = false
        }

        if (signupViewModel.password.value.isNullOrEmpty()) {
            passwordInput.error = "Password tidak boleh kosong"
            status = false
        }

        if (signupViewModel.confPassword.value.isNullOrEmpty()) {
            passwordConfInput.error = "Konfirmasi Password tidak boleh kosong"
            status = false
        }

        if (!signupViewModel.password.value.equals(signupViewModel.confPassword.value)) {
            passwordConfInput.error = "Password tidak sama"
            status = false
        }

        return status
    }

    private fun initProgressDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.progress_dialog, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        alertDialog = dialogBuilder.create()
    }
}