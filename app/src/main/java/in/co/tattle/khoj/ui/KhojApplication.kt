package `in`.co.tattle.khoj.ui

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.utils.Constants
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.yariksoffice.lingver.Lingver

class KhojApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Lingver.init(this, "EN")
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.query_updates)
            val descriptionText = getString(R.string.query_updates_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constants.QUERY_CHANNEL, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}