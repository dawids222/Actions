package pl.grajek.actions.view.activity

import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.content_notification.*
import pl.grajek.actions.R
import pl.grajek.actions.databinding.ActivityNotificationBinding
import pl.grajek.actions.model.SnackbarProvider
import pl.grajek.actions.util.format
import pl.grajek.actions.viewmodel.NotificationViewModel


class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationViewModel: NotificationViewModel
    private val timeListener = getTimeListener()
    private val snackbarProvider = SnackbarProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityNotificationBinding>(
            this,
            R.layout.activity_notification
        )

        title = getString(R.string.notifications_activity_title)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        notificationViewModel = ViewModelProviders.of(this).get(NotificationViewModel::class.java)
        binding.vm = notificationViewModel

        setListeners()
        setObservers()
    }

    private fun setListeners() {
        timeInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showTimePicker()
            }
        }

        timeInput.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showTimePicker() {
        TimePickerDialog(this, timeListener, 0, 0, true).show()
    }

    private fun setObservers() {
        notificationViewModel.isEnabled.observe(this, Observer {
            val enabled = it ?: false
            enableViews(enabled)
        })

        notificationViewModel.snackbarModel.observe(this, Observer { snackbarModel ->
            snackbarModel?.let {
                snackbarProvider.create(
                    notificationRootLayout, it.message, it.short,
                    ContextCompat.getColor(this, it.color)
                ).show()
            }
        })
    }

    private fun enableViews(enabled: Boolean) {
        timeInput.isEnabled = enabled
        intervalInput.isEnabled = enabled
    }

    private fun getTimeListener(): TimePickerDialog.OnTimeSetListener {
        return TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val hours = format(hourOfDay)
            val minutes = format(minute)
            timeInput.setText("$hours:$minutes")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
