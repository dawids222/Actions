package pl.grajek.actions.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_graph.*
import pl.grajek.actions.R
import pl.grajek.actions.databinding.ActivityGraphBinding
import pl.grajek.actions.model.ChartDrawer
import pl.grajek.actions.model.entity.Category
import pl.grajek.actions.viewmodel.GraphViewModel


class GraphActivity : AppCompatActivity() {

    companion object {
        const val CATEGORY = "CATEGORY"
    }

    private lateinit var graphViewModel: GraphViewModel
    private lateinit var chartDrawer: ChartDrawer
    private lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGraphBinding>(this, R.layout.activity_graph)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        graphViewModel = ViewModelProviders.of(this).get(GraphViewModel::class.java)
        binding.vm = graphViewModel

        chartDrawer = ChartDrawer(chart, this)

        handleBundle()
        setTitle()
        setObservers()
    }

    private fun handleBundle() {
        if (intent.hasExtra(CATEGORY)) {
            category = intent.getSerializableExtra(CATEGORY) as Category
        }
    }

    private fun setTitle() {
        title = category.name
    }

    private fun setObservers() {
        graphViewModel.select(category.id!!).observe(this, Observer {
            chartDrawer.draw(it!!)
        })
    }

    private fun zoomOut() {
        chart.fitScreen()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.graph, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.zoom_out -> zoomOut()
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }
}
