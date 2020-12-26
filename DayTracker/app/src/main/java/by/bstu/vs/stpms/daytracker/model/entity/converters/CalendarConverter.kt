package by.bstu.vs.stpms.daytracker.model.entity.converters

import androidx.room.TypeConverter
import java.util.*

class CalendarConverter {

    @TypeConverter
    fun fromCalendar(calendar: Calendar): Long {
        return calendar.time.time
    }

    @TypeConverter
    fun toCalendar(long: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = Date(long)
        return calendar
    }

}