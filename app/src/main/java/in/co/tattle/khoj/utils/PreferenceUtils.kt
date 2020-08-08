package `in`.co.tattle.khoj.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object PreferenceUtils {

    private const val PREF_NAME: String = "khojPreferences"

    const val IS_LOGGED_IN_USER: String = "isLoggedInUser"
    const val SELECTED_LANGUAGE: String = "selectedLanguage"
    const val APP_CREATOR_TOKEN: String = "creatorToken"
    const val USER_TOKEN: String = "userToken"
    const val USER_ID: String = "userId"
    const val FCM_DEVICE_TOKEN: String = "fcmToken"

    fun getPrefString(context: Context, prefKey: String): String? {
        val preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        return preferences.getString(prefKey, null)
    }

    fun getPrefBoolean(context: Context, prefKey: String): Boolean {
        val preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        return preferences.getBoolean(prefKey, false)
    }

    fun setPrefString(context: Context, prefKey: String, prefValue: String) {
        val preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        preferences.edit().putString(prefKey, prefValue).apply()
    }

    fun setPrefBoolean(context: Context, prefKey: String, prefValue: Boolean) {
        val preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        preferences.edit().putBoolean(prefKey, prefValue).apply()
    }

}