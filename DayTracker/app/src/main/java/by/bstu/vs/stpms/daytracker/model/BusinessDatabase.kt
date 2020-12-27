package by.bstu.vs.stpms.daytracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.bstu.vs.stpms.daytracker.model.dao.BusinessDao
import by.bstu.vs.stpms.daytracker.model.entity.Business

@Database(entities = [Business::class], version = 4, exportSchema = false)
abstract class BusinessDatabase : RoomDatabase() {

    abstract fun businessDao(): BusinessDao

    companion object {
        @Volatile
        private var INSTANCE: BusinessDatabase? = null

        fun getDatabase(context: Context): BusinessDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BusinessDatabase::class.java,
                    "business_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance.openHelper.writableDatabase.execSQL(insertTrigger)
                instance.openHelper.writableDatabase.execSQL(updateTrigger)
                instance
            }
        }

        val insertTrigger = "create trigger if not exists check_time_ranges_insert before insert on business\n" +
                "begin\n" +
                "    select case WHEN (select count(*) from business\n" +
                "    where (new.start_time > start_time and new.start_time < end_time) or (new.end_time > start_time and new.end_time < end_time) or (start_time > new.start_time and start_time < new.end_time)) > 0 THEN\n" +
                "    raise (fail, 'Time interval intersection')\n" +
                "    end;\n" +
                "end;\n"
        val updateTrigger = "create trigger if not exists check_time_ranges_update before update on business\n" +
                "begin\n" +
                "    select case WHEN (select count(*) from business\n" +
                "    where (new.start_time > start_time and new.start_time < end_time) or (new.end_time > start_time and new.end_time < end_time) or (start_time > new.start_time and start_time < new.end_time)) > 0 THEN\n" +
                "    raise (fail, 'Time interval intersection')\n" +
                "    end;\n" +
                "end;\n"
    }
}

