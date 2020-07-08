package `in`.co.tattle.khoj.ui

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.home.HomepageActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    lateinit var runnable: Runnable
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //start handler to go to next screen
        startHandler()

        //animate to make the text visible
        changeTextAlpha()
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

    private fun startNextActivity() {
        startActivity(Intent(this, HomepageActivity::class.java))
    }

    private fun startHandler() {
        handler = Handler()
        runnable = Runnable {
            startNextActivity()
        }
        handler.postDelayed(runnable, 2000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }
}