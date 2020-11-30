package com.example.urwood.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.urwood.ui.main.MainActivity
import com.example.urwood.R
import com.example.urwood.repository.datasource.auth.other.AuthRepoImpl
import com.example.urwood.ui.auth.AuthActivity
import com.example.urwood.ui.auth.domain.AuthImpl
import com.example.urwood.utils.Constants
import com.example.urwood.utils.viewobject.Resource

class SplashScreenActivity : AppCompatActivity() {

    private val splashScreenViewModel: SplashScreenViewModel by lazy {
        ViewModelProvider(
                this,
                SplashScreenVMFactory(AuthImpl(AuthRepoImpl()))
        ).get(SplashScreenViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash_screen)

        splashScreenViewModel.authInstance.observe(this, Observer {
            when (it) {
                is Resource.Success -> if (it.data.currentUser != null) {
                    navigateToMainActivity()
                } else {
//                    navigateToCameraActivity()
                    navigateToAuthActivity()
                }

                is Resource.Failure -> {
                    Thread.sleep(Constants.SPLASH_SCREEN_DELAY)
                    navigateToMainActivity()
                }
                is Resource.Loading -> TODO()
            }
        })
    }

    private fun navigateToAuthActivity() {
        finish()
        startActivity(Intent(this, AuthActivity::class.java))
        Log.d("SplashScreenIntent", "SplashScreenIntent: To Auth")
    }

    private fun navigateToMainActivity() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
        Log.d("SplashScreenIntent", "SplashScreenIntent: To Main")
    }

}