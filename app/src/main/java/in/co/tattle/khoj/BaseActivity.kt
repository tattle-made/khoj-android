package `in`.co.tattle.khoj

import `in`.co.tattle.khoj.utils.LocalizationUtil
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocalizationUtil.applyLanguage(newBase))
    }
}