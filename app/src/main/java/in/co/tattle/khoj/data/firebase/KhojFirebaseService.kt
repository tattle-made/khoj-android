package `in`.co.tattle.khoj.data.firebase

import `in`.co.tattle.khoj.R
import `in`.co.tattle.khoj.ui.message.response.MessageResponseActivity
import `in`.co.tattle.khoj.utils.Constants
import `in`.co.tattle.khoj.utils.PreferenceUtils
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BigTextStyle
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class KhojFirebaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        PreferenceUtils.setPrefString(this, PreferenceUtils.FCM_DEVICE_TOKEN, token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        showNotification(remoteMessage.data)
    }

    private fun showNotification(
        data: MutableMap<String, String>
    ) {
        val intent = Intent(this, MessageResponseActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(Constants.MESSAGE_ID, data["queryId"])
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, Constants.QUERY_CHANNEL)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(data["title"])
            .setContentText(data["body"])
            .setStyle(BigTextStyle().bigText(data["body"]))
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}