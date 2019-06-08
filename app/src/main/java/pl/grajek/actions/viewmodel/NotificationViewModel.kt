package pl.grajek.actions.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import pl.grajek.actions.R
import pl.grajek.actions.model.Date
import pl.grajek.actions.model.SingleLiveEvent
import pl.grajek.actions.model.dto.SnackbarModel
import pl.grajek.actions.model.notification.NotificationScheduler
import pl.grajek.actions.model.preference.PreferenceProvider
import pl.grajek.actions.util.HOUR

class NotificationViewModel(application: Application) : AndroidViewModel(application) {

    private val notificationScheduler = NotificationScheduler(application)
    private val preferencesProvider = PreferenceProvider(application)
    val snackbarModel = SingleLiveEvent<SnackbarModel>()

    val isEnabled = MutableLiveData<Boolean>()
    val time = MutableLiveData<String>()
    val interval = MutableLiveData<String>()

    init {
        getViewState()
    }

    private fun getViewState() {
        isEnabled.value = preferencesProvider.getNotificationsEnabled()
        time.value = preferencesProvider.getNotificationFirstTime()
        interval.value = preferencesProvider.getNotificationsInterval()
    }

    private fun saveViewState() {
        preferencesProvider.setNotificationEnabled(isEnabled.value!!)
        preferencesProvider.setNotificationFirstTime(time.value!!)
        preferencesProvider.setNotificationsInterval(interval.value!!)
    }

    fun confirm() {
        saveViewState()
        if (isEnabled.value == false)
            disableNotifications()
        else
            setNotifications()
    }

    private fun disableNotifications() {
        notificationScheduler.stopReminderNotifications()
        setSnackbar(R.string.notification_disabled, R.color.colorSuccess)
    }

    private fun setNotifications() {
        val startDate = Date.parseFromTime(time.value!!)
        val interval = this.interval.value?.toLong()?.times(HOUR) ?: 24 * HOUR
        notificationScheduler.scheduleReminderNotifications(startDate, interval)
        setSnackbar(R.string.notification_set, R.color.colorSuccess)
    }

    private fun setSnackbar(message: Int, color: Int) {
        snackbarModel.value = SnackbarModel(message, color)
    }

    class TimeDetails(val hours: Int, val minutes: Int)

    fun timeDetails(): TimeDetails {
        val time = time.value!!
        val hours = time.substring(0, 2).toInt()
        val minutes = time.substring(3, 5).toInt()
        return TimeDetails(hours, minutes)
    }
}