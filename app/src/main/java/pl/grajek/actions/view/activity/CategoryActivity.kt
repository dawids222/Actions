package pl.grajek.actions.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_category.*
import pl.grajek.actions.R
import pl.grajek.actions.databinding.ActivityCategoryBinding
import pl.grajek.actions.viewmodel.CategoryViewModel


class CategoryActivity : AppCompatActivity() {

    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCategoryBinding>(this, R.layout.activity_category)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        binding.vm = categoryViewModel

        setObservers()
    }

    private fun setObservers() {
        categoryViewModel.errorMessage.observe(this, Observer {
            val snackbar = Snackbar.make(rootLayout, it!!, Snackbar.LENGTH_SHORT)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorError))
            snackbar.show()
        })

        categoryViewModel.goBack.observe(this, Observer {
            if (it == true)
                onBackPressed()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
