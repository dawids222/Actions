package pl.grajek.actions.model.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = NotificationManager()
        context?.let { notificationManager.showReminderNotification(it) }
    }
}