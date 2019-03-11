package pl.grajek.actions.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import pl.grajek.actions.model.notification.NotificationScheduler

class NotificationViewModel(application: Application) : AndroidViewModel(application) {

    private val notificationScheduler = NotificationScheduler(application)

    val isEnabled = MutableLiveData<Boolean>()
    val time = MutableLiveData<String>()

    init {
        isEnabled.value = false
    }

    fun confirm() {
        // todo
    }
}