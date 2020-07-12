package `in`.co.tattle.khoj.utils

import android.content.Context
import android.content.res.Configuration
import android.text.TextUtils
import java.util.*

object LocalizationUtil {

    fun applyLanguage(context: Context): Context {
        //get saved language from preferences
        val language: String = PreferenceUtils.getPrefString(
            context,
            PreferenceUtils.SELECTED_LANGUAGE
        ).toString()
        if (TextUtils.isEmpty(language)) {
            return context
        }
        val savedLocale = Locale(language)

        // as part of creating a new context we also need to override the default locale.
        Locale.setDefault(savedLocale)

        // creating new configuration with the pref locale
        val newConfig = Configuration()
        newConfig.setLocale(savedLocale)
        return context.createConfigurationContext(newConfig)
    }
}