package `in`.co.tattle.khoj.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*


object LocalizationUtil {
/*

    public static void setLocale(Context c) {
        setNewLocale(c, getLanguage(c));
    }

    public static void setNewLocale(Context c, String language) {
        persistLanguage(c, language);
        updateResources(c, language);
    }

    public static String getLanguage(Context c) { ... }

    private static void persistLanguage(Context c, String language) { ... }

*/

    private fun updateResources(
        context: Context,
        language: String
    ): Context? {
        var context = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config =
            Configuration(res.configuration)
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
        } else {
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
        }
        return context
    }
}