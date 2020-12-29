package by.bstu.vs.stpms.daytracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.bstu.vs.stpms.daytracker.model.dao.BusinessDao
import by.bstu.vs.stpms.daytracker.model.entity.Business

@Database(entities = [Business::class], version = 5, exportSchema = false)
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

        private const val insertTrigger = "create trigger if not exists check_time_ranges_insert before insert on business\n" +
                "begin\n" +
                "    select case " +
                "    WHEN strftime('%s', 'now') < cast(new.end_time as text) THEN raise(fail, \"End can't be more than now\")\n" +
                "    WHEN new.start_time > new.end_time THEN raise(fail, \"Begin can't be after end\")" +
                "    WHEN (select count(*) from business\n" +
                "    where (new.start_time > start_time and new.start_time < end_time) or (new.end_time > start_time and new.end_time < end_time) or (start_time > new.start_time and start_time < new.end_time)) > 0 THEN\n" +
                "    raise (fail, 'Time interval intersection')\n" +
                "    end;\n" +
                "end;\n"
        private const val updateTrigger = "create trigger if not exists check_time_ranges_update before update on business\n" +
                "begin\n" +
                "    select case " +
                "    WHEN strftime('%s', 'now') < cast(new.end_time as text) THEN raise(fail, \"End can't be more than now\")\n" +
                "    WHEN new.start_time > new.end_time THEN raise(fail, \"Begin can't be after end\")" +
                "    WHEN (select count(*) from business\n" +
                "    where (new.start_time > start_time and new.start_time < end_time) or (new.end_time > start_time and new.end_time < end_time) or (start_time > new.start_time and start_time < new.end_time)) > 0 THEN\n" +
                "    raise (fail, 'Time interval intersection')\n" +
                "    end;\n" +
                "end;\n"
    }
}

