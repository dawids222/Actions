package pl.grajek.actions.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_graph.*
import pl.grajek.actions.R
import pl.grajek.actions.databinding.ActivityGraphBinding
import pl.grajek.actions.model.ChartDrawer
import pl.grajek.actions.viewmodel.GraphViewModel


class GraphActivity : AppCompatActivity() {

    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"
    }

    private lateinit var graphViewModel: GraphViewModel
    private lateinit var chartDrawer: ChartDrawer
    private var categoryId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGraphBinding>(this, R.layout.activity_graph)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        graphViewModel = ViewModelProviders.of(this).get(GraphViewModel::class.java)
        binding.vm = graphViewModel

        chartDrawer = ChartDrawer(chart, this)

        handleBundle()
        setObservers()
    }

    private fun handleBundle() {
        if (intent.hasExtra(CATEGORY_ID)) {
            categoryId = intent.getLongExtra(CATEGORY_ID, -1)
        }
    }

    private fun setObservers() {
        graphViewModel.select(categoryId).observe(this, Observer {
            chartDrawer.draw(it!!)
        })
    }
}
