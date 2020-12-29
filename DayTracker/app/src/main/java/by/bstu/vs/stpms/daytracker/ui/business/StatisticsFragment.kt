package by.bstu.vs.stpms.daytracker.ui.business

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.bstu.vs.stpms.daytracker.R
import by.bstu.vs.stpms.daytracker.model.entity.Business
import by.bstu.vs.stpms.daytracker.model.entity.BusinessType
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.lang.StringBuilder
import java.util.*


class StatisticsFragment : Fragment() {

    private lateinit var businessViewModel: BusinessViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        businessViewModel =
                ViewModelProvider(this).get(BusinessViewModel::class.java)

        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val names = BusinessType.values().toList().map { capitalize(it.name) }
        val colors = ArrayList<Int>().apply {
            add(Color.parseColor("#304567"))
            add(Color.parseColor("#309967"))
            add(Color.parseColor("#476567"))
            add(Color.parseColor("#890567"))
            add(Color.parseColor("#a35567"))
            add(Color.parseColor("#ff5f67"))
        }

        val pieChart = requireView().findViewById<PieChart>(R.id.chart)
        val recommendationsTextView = requireView().findViewById<TextView>(R.id.recommendations)

        businessViewModel.businessesLiveData.observe(viewLifecycleOwner, {
            val map = formatList(it)
            val pieEntries: ArrayList<PieEntry> = ArrayList()
            for (key: String in map.keys) {
                pieEntries.add(PieEntry(map[key]?.toFloat() ?: error("map out of range"), key))
            }
            val pieDataSet = PieDataSet(pieEntries, "Hours for last week")
            pieDataSet.valueTextSize = 12f
            pieDataSet.colors = colors
            val pieData = PieData(pieDataSet)
            pieData.setDrawValues(true)
            pieChart.data = pieData
            pieChart.invalidate()

            val recommendations = StringBuilder()

            if(map[capitalize(BusinessType.CHILL.name)] ?: 0.0 < 14) {
                recommendations.append(getString(R.string.too_few_chill))
            }
            if(map[capitalize(BusinessType.ROAD.name)] ?: 0.0 > 28) {
                recommendations.append(getString(R.string.too_much_road))
            }
            if(map[capitalize(BusinessType.STUDYING.name)] ?: 0.0 < 14) {
                recommendations.append(getString(R.string.too_few_studying))
            }
            if(map[capitalize(BusinessType.WORK.name)] ?: 0.0 > 40) {
                recommendations.append(getString(R.string.too_much_work))
            }
            if(map[capitalize(BusinessType.SLEEP.name)] ?: 0.0 < 56) {
                recommendations.append(getString(R.string.too_few_sleep))
            }

            recommendationsTextView.text = recommendations.toString()
        })
    }


}

fun formatList(busies: List<Business>): Map<String, Double> {
    val weekAgo = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -7)
    }
    return busies
        .filter { it.startTime.compareTo(weekAgo) == 1 }
        .groupBy { capitalize(it.type.name) }
        .mapValues { busiesGroupByType ->
            busiesGroupByType
                .value
                .sumByDouble { getDeltaInHours(it.startTime, it.endTime) }
        }
}

fun capitalize(input: String) = input.substring(0, 1) + input.toLowerCase().substring(1)
fun getDeltaInHours(start: Calendar, end: Calendar) = ((end.timeInMillis - start.timeInMillis) / 1000.0) / 3600
