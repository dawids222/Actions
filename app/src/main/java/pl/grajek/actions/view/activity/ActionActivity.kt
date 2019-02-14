package pl.grajek.actions.view.activity

import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_action.*
import kotlinx.android.synthetic.main.content_action.*
import pl.grajek.actions.R
import pl.grajek.actions.databinding.ActivityActionBinding
import pl.grajek.actions.model.Date
import pl.grajek.actions.model.entity.Action
import pl.grajek.actions.viewmodel.ActionViewModel


class ActionActivity : AppCompatActivity() {

    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"
        const val ACTION = "ACTION"
    }

    private lateinit var actionViewModel: ActionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityActionBinding>(this, R.layout.activity_action)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        actionViewModel = ViewModelProviders.of(this).get(ActionViewModel::class.java)
        binding.vm = actionViewModel

        setObservers()
        setListeners()
        handleBundle()
    }

    private fun handleBundle() {
        if (intent.hasExtra(CATEGORY_ID)) { // Handle add action
            val categoryId = intent.getLongExtra(CATEGORY_ID, -1)
            actionViewModel.categoryId = categoryId
        } else if (intent.hasExtra(ACTION)) { // Handle modify action
            val action = intent.getSerializableExtra(ACTION) as Action
            actionViewModel.setActionToModify(action)
        }
    }

    private fun setListeners() {
        dateInput.setOnClickListener {
            handleDateInput()
        }

        dateInput.setOnFocusChangeListener { view: View, b: Boolean ->
            if (view.hasFocus())
                handleDateInput()
        }
    }

    private fun handleDateInput() {
        val date = Date()
        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val formattedDate = "$dayOfMonth/$month/$year"
                dateInput.setText(formattedDate)
                amountInput.requestFocus()
            }, date.year, date.month, date.day
        )
        datePickerDialog.show()
    }

    private fun setObservers() {
        actionViewModel.errorMessage.observe(this, Observer {
            val snackbar = Snackbar.make(rootLayout, it!!, Snackbar.LENGTH_SHORT)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorError))
            snackbar.show()
        })

        actionViewModel.goBack.observe(this, Observer {
            if (it == true)
                onBackPressed()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
