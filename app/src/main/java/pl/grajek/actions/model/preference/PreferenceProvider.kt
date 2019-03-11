package pl.grajek.actions.model.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import pl.grajek.actions.util.edit

class PreferenceProvider(private val context: Context) {

    fun getNotificationsEnabled(): Boolean {
        return sharedPrefs(context).getBoolean(NOTIFICATIONS_ENABLED, NOTIFICATIONS_ENABLED_DEFAULT)
    }

    fun setNotificationEnabled(value: Boolean) {
        sharedPrefs(context).edit { it.putBoolean(NOTIFICATIONS_ENABLED, value) }
    }

    fun getNotificationFirstTime(): String {
        return sharedPrefs(context).getString(NOTIFICATION_FIRST_TIME, NOTIFICATION_FIRST_TIME_DEFAULT)!!
    }

    fun setNotificationFirstTime(value: String) {
        sharedPrefs(context).edit { it.putString(NOTIFICATION_FIRST_TIME, value) }
    }

    fun getNotificationsInterval(): String {
        return sharedPrefs(context).getString(NOTIFICATIONS_INTERVAL, NOTIFICATIONS_INTERVAL_DEFAULT)!!
    }

    fun setNotificationsInterval(value: String) {
        sharedPrefs(context).edit { it.putString(NOTIFICATIONS_INTERVAL, value) }
    }

    private fun sharedPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(SPK, MODE_PRIVATE)

    private companion object {
        const val SPK = "ACTIONS_SHARED_PREFERENCES_KEY"
        const val NOTIFICATIONS_ENABLED = "NOTIFICATIONS_ENABLED"
        const val NOTIFICATION_FIRST_TIME = "NOTIFICATION_FIRST_TIME"
        const val NOTIFICATIONS_INTERVAL = "NOTIFICATIONS_INTERVAL"

        const val NOTIFICATIONS_ENABLED_DEFAULT = false
        const val NOTIFICATION_FIRST_TIME_DEFAULT = "00:00"
        const val NOTIFICATIONS_INTERVAL_DEFAULT = ""
    }
}