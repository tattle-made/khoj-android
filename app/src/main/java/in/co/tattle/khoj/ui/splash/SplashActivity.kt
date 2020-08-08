package `in`.co.tattle.khoj.ui.splash

import `in`.co.tattle.khoj.BaseActivity
import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.home.HomepageActivity
import `in`.co.tattle.khoj.ui.setup.SetupActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {
    lateinit var runnable: Runnable
    lateinit var handler: Handler
    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        //animate to make the text visible
        changeTextAlpha()

        setupObserver()
    }

    private fun setupObserver() {
        viewModel.isLoggedInUser.observe(this, Observer { isLoggedInUser ->
            if (isLoggedInUser) {
                startHomeActivity()
            } else {
                startSetupActivity()
            }
        })
    }

    private fun changeTextAlpha() {
        welcomeText.animate().apply {
            interpolator = LinearInterpolator()
            duration = 500
            alpha(1f)
            startDelay = 500
            start()
        }
    }

    private fun startSetupActivity() {
        val intent = Intent(this, SetupActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun startHomeActivity() {
        val intent = Intent(this, HomepageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}