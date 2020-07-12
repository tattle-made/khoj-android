package `in`.co.tattle.khoj.ui

import android.app.Application
import android.content.Context
import `in`.co.tattle.khoj.utils.LocalizationUtil

class KhojApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocalizationUtil.applyLanguage(base))
    }

}