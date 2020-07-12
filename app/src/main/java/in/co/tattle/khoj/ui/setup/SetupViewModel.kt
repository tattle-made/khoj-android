package `in`.co.tattle.khoj.ui.setup

import `in`.co.tattle.khoj.utils.PreferenceUtils
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class SetupViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    fun setupLanguage(language: String) {
        PreferenceUtils.setPrefString(context, PreferenceUtils.SELECTED_LANGUAGE, language)
        PreferenceUtils.setPrefBoolean(context, PreferenceUtils.IS_LOGGED_IN_USER, true)
    }
}