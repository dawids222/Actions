package pl.grajek.actions.view.activity

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_action.*
import kotlinx.android.synthetic.main.content_action.*
import pl.grajek.actions.R
import pl.grajek.actions.databinding.ActivityActionBinding
import pl.grajek.actions.viewmodel.ActionViewModel
import java.text.SimpleDateFormat
import java.util.*


class ActionActivity : AppCompatActivity() {

    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"
    }

    lateinit var actionViewModel: ActionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityActionBinding>(this, R.layout.activity_action)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        actionViewModel = ViewModelProviders.of(this).get(ActionViewModel::class.java)
        binding.vm = actionViewModel

        setListeners()
    }

    fun setListeners() {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date = sdf.format(Date(System.currentTimeMillis())).toString().split('/')
        val year = date[2].toInt()
        val month = date[1].toInt() - 1
        val day = date[0].toInt()
        dateInput.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    val formattedDate = "$dayOfMonth/$month/$year"
                    dateInput.setText(formattedDate)
                    dateInput.clearFocus()
                }
            }, year, month, day)
            Log.e("TAg", date.toString())
            datePickerDialog.show()
        }
    }
}
