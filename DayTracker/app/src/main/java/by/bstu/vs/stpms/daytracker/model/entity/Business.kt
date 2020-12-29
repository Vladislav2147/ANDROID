package by.bstu.vs.stpms.daytracker.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import by.bstu.vs.stpms.daytracker.model.entity.converters.BusinessTypeConverter
import by.bstu.vs.stpms.daytracker.model.entity.converters.CalendarConverter
import java.io.Serializable

import java.util.*

@Entity(tableName = "business")
@TypeConverters(BusinessTypeConverter::class, CalendarConverter::class)
class Business(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "start_time")
    var startTime: Calendar,
    @ColumnInfo(name = "end_time")
    var endTime: Calendar,
    var type: BusinessType
): Serializable {

    constructor() : this(
            null,
            Calendar.getInstance().apply {
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            },
            Calendar.getInstance().apply {
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            },
            BusinessType.CHILL
    )

}