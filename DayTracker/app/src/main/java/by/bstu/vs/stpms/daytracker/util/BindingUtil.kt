package by.bstu.vs.stpms.daytracker.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import by.bstu.vs.stpms.daytracker.R
import by.bstu.vs.stpms.daytracker.model.entity.BusinessType
import java.text.SimpleDateFormat
import java.util.*

object BindingUtil {
    private val formatter = SimpleDateFormat("HH:mm dd.MM.yyyy")
    private val timeOnlyFormatter = SimpleDateFormat("HH:mm ")
    @JvmStatic
    @BindingAdapter("android:text")
    fun setCalendar(textView: TextView, calendar: Calendar) {
        val now = Calendar.getInstance()
        val delta = now[Calendar.DAY_OF_YEAR] - calendar[Calendar.DAY_OF_YEAR]
        val dateTimeString = StringBuilder()
        if (delta > 7) {
            dateTimeString.append(formatter.format(calendar.time))
        } else {
            dateTimeString.append(timeOnlyFormatter.format(calendar.time))
            when (delta) {
                0 -> dateTimeString.append("today")
                1 -> dateTimeString.append("yesterday")
                else -> dateTimeString.append(delta).append(" days ago")
            }
        }
        textView.text = dateTimeString.toString()
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageResource(imageView: ImageView, type: BusinessType?) {
        var imageID = 0
        when (type) {
            BusinessType.CHILL -> imageID = R.drawable.business_chill
            BusinessType.WORK -> imageID = R.drawable.business_work
            BusinessType.STUDYING -> imageID = R.drawable.business_studying
            BusinessType.ROAD -> imageID = R.drawable.business_road
            BusinessType.LUNCH -> imageID = R.drawable.business_lunch
            BusinessType.SLEEP -> imageID = R.drawable.business_sleep
        }
        imageView.setImageResource(imageID)
    }
}