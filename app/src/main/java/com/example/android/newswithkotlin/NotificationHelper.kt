package com.example.android.newswithkotlin

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import android.util.Log

//reference: https://www.androidauthority.com/android-8-0-oreo-app-implementing-notification-channels-801097/
class NotificationHelper(base: Context) : ContextWrapper(base) {

    private var notifManager: NotificationManager? = null
    private val manager: NotificationManager
        get() {
            if (notifManager == null) {
                notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            return notifManager!!
        }

    //Create your notification channels//
    init {
        createChannels()
    }

    fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_NEWS_LIST_ID,
                    CHANNEL_NEWS_LIST, IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.setShowBadge(true)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            manager.createNotificationChannel(notificationChannel)
        }
    }

    //Create the notification that’ll be posted to Channel NewsList//
    fun getNotification(title: String, body: String, intent: PendingIntent): Notification.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(applicationContext, CHANNEL_NEWS_LIST_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.ic_warning_black_24dp)
                    .setAutoCancel(true)
                    .setContentIntent(intent)
        } else {
            Notification.Builder(applicationContext)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.ic_warning_black_24dp)
                    .setAutoCancel(true)
                    .setContentIntent(intent)
        }
    }

    fun notify(id: Int, notification: Notification) {
        Log.d("my_tag", "notify called")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL
            manager.notify(id, notification)
        } else {
            manager.notify(NEWS_LIST_NOTIFICATION_ID, notification)
        }
    }

    companion object {
        val CHANNEL_NEWS_LIST_ID = "com.example.android.newswithkotlin.NewsList"
        val CHANNEL_NEWS_LIST = "Channel News"
        const val NEWS_LIST_NOTIFICATION_ID = 1027

    }
}