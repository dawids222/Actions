package pl.grajek.actions

import android.app.Application
import pl.grajek.actions.model.NotificationManager

class ActionsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val notificationManager = NotificationManager()
        notificationManager.createNotificationChannel(this)
    }
}