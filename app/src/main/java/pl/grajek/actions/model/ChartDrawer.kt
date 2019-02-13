package pl.grajek.actions.model

import android.content.Context
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import pl.grajek.actions.model.entity.Action
import pl.grajek.actions.util.isPortraitMode
import pl.grajek.actions.util.sdf

class ChartDrawer(
    private val chart: LineChart,
    context: Context
) {

    init {
        configure(context)
    }

    private fun configure(context: Context) {
        val xAxis = chart.xAxis
        xAxis.granularity = 1f
        xAxis.labelCount = if (isPortraitMode(context)) 3 else 5
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        //xAxis.labelRotationAngle = 325f
        chart.animateX(1000)
        chart.axisRight.isEnabled = false
        chart.legend.isEnabled = false
    }

    fun draw(actions: MutableList<Action>) {
        val dataSet = createDataSet(actions)
        val lineData = LineData(dataSet)
        chart.data = lineData
        setupValueFormatter(actions)
        chart.invalidate()
    }

    private fun createDataSet(actions: MutableList<Action>): LineDataSet {
        val entries = List(actions.size) {
            Entry(it.toFloat(), actions[it].quantity.toFloat())
        }
        val dataSet = LineDataSet(entries, "")
        dataSet.valueTextSize = 9f
        dataSet.circleRadius = 4f

        return dataSet
    }

    private fun setupValueFormatter(actions: MutableList<Action>) {
        val xAxis = chart.xAxis
        xAxis.valueFormatter = object : IAxisValueFormatter {
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                return sdf.format(actions[value.toInt()].date)
            }
        }
    }
}