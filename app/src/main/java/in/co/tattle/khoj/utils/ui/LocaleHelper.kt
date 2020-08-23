package `in`.co.tattle.khoj.utils.ui

import `in`.co.tattle.khoj.utils.PreferenceUtils
import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*


object LocaleHelper {

    /* fun setLocale(context: Context): Context {
         val language: String? = getPersistedData(context)
         return updateResources(context, language)
     }

     private fun getPersistedData(context: Context): String? {
         return PreferenceUtils.getPrefString(context, PreferenceUtils.SELECTED_LANGUAGE)
     }

     private fun updateResources(context: Context, language: String?): Context {
         val locale = Locale(language!!)
         Locale.setDefault(locale)
         val configuration: Configuration = context.resources.configuration
         configuration.setLocale(locale)
         configuration.setLayoutDirection(locale)
         return context.createConfigurationContext(configuration)
     }*/

    fun onAttach(context: Context): Context? {
        var lang = getPersistedData(context, Locale.getDefault().language)
        if (lang == null) {
            lang = "en"
        }
        return setLocale(context, lang)
    }

    /*fun onAttach(
        context: Context,
        defaultLanguage: String
    ): Context? {
        val lang = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }*/

    fun setLocale(
        context: Context,
        language: String?
    ): Context? {
        return updateResources(context, language)
    }

    private fun getPersistedData(
        context: Context,
        defaultLanguage: String
    ): String? {
        return PreferenceUtils.getPrefString(context, PreferenceUtils.SELECTED_LANGUAGE)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(
        context: Context,
        language: String?
    ): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration =
            Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }
}