package pl.grajek.actions.model.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import java.util.*

class NotificationScheduler(val context: Context) {

    private companion object {
        const val REQUEST_CODE = 460443163
    }

    private val alarmManager: AlarmManager
        get() = context.getSystemService(ALARM_SERVICE) as AlarmManager

    private fun getPendingReminderIntent(): PendingIntent {
        val appContext = context.applicationContext
        val intent = Intent(appContext, NotificationReceiver::class.java)
        return PendingIntent.getBroadcast(appContext, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun scheduleReminderNotifications(startDate: Date, interval: Long = AlarmManager.INTERVAL_DAY) {
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startDate.time, interval, getPendingReminderIntent())
    }

    fun stopReminderNotifications() {
        alarmManager.cancel(getPendingReminderIntent())
    }
}