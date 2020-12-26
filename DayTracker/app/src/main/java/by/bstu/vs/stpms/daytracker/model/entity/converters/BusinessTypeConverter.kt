package by.bstu.vs.stpms.daytracker.model.entity.converters

import androidx.room.TypeConverter
import by.bstu.vs.stpms.daytracker.model.entity.BusinessType

class BusinessTypeConverter {

    @TypeConverter
    fun toBusinessType(int: Int) = BusinessType.values()[int]

    @TypeConverter
    fun fromBusinessType(businessType: BusinessType) = businessType.ordinal

}