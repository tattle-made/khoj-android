package `in`.co.tattle.khoj.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object Preferences {

    private const val PREF_NAME: String = "khojPreferences"

    fun getPrefString(context: Context, prefKey: String): String? {
        val preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        return preferences.getString(prefKey, null)
    }

}