package pl.grajek.actions.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_graph.*
import pl.grajek.actions.R
import pl.grajek.actions.databinding.ActivityGraphBinding
import pl.grajek.actions.model.entity.Action
import pl.grajek.actions.util.sdf
import pl.grajek.actions.viewmodel.GraphViewModel
import java.util.*


class GraphActivity : AppCompatActivity() {

    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"
    }

    private lateinit var graphViewModel: GraphViewModel
    private var categoryId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGraphBinding>(this, R.layout.activity_graph)

        graphViewModel = ViewModelProviders.of(this).get(GraphViewModel::class.java)
        binding.vm = graphViewModel

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
            drawGraph(it!!)
        })
    }

    val number = 10
    private fun drawGraph(actions: MutableList<Action>) {
        val data = Array(actions.count()) {
            val action = actions[it]
            DataPoint(action.date, action.quantity)
        }

        val series = LineGraphSeries<DataPoint>(data)

        graph.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                if (isValueX) {
                    return sdf.format(Date(value.toLong()))
                }
                return super.formatLabel(value, isValueX)
            }
        }
        graph.gridLabelRenderer.numHorizontalLabels = 3

        graph.viewport.setMinX(actions[0].date.time.toDouble())
        graph.viewport.setMaxX(actions[actions.count() - 1].date.time.toDouble())
        graph.viewport.isXAxisBoundsManual = true

        graph.gridLabelRenderer.setHumanRounding(false)

        graph.viewport.isScalable = true
        graph.viewport.setScalableY(true)

        graph.addSeries(series)
    }
}
