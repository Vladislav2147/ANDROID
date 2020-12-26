package by.bstu.vs.stpms.daytracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.bstu.vs.stpms.daytracker.model.dao.BusinessDao
import by.bstu.vs.stpms.daytracker.model.entity.Business

@Database(entities = [Business::class], version = 1, exportSchema = false)
abstract class BusinessDatabase : RoomDatabase() {

    abstract fun businessDao(): BusinessDao

    companion object {
        @Volatile
        private var INSTANCE: BusinessDatabase? = null

        fun getDatabase(context: Context): BusinessDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BusinessDatabase::class.java,
                    "business_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}