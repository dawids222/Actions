package pl.grajek.actions.view.activity

import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_notification.*
import pl.grajek.actions.R
import pl.grajek.actions.databinding.ActivityNotificationBinding
import pl.grajek.actions.util.format
import pl.grajek.actions.viewmodel.NotificationViewModel


class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationViewModel: NotificationViewModel
    private val timeListener = getTimeListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityNotificationBinding>(
            this,
            R.layout.activity_notification
        )

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
    }

    private fun enableViews(enabled: Boolean) {
        timeInput.isEnabled = enabled
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
