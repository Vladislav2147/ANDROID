package by.bstu.vs.stpms.daytracker.model.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.room.*
import by.bstu.vs.stpms.daytracker.model.entity.Business
import kotlinx.coroutines.flow.Flow


@Dao
interface BusinessDao {

    @Insert
    @Throws(SQLiteConstraintException::class)
    suspend fun insert(business: Business)

    @Query("SELECT * FROM business ORDER BY start_time DESC")
    fun getAll(): Flow<List<Business>>

    @Update
    @Throws(SQLiteConstraintException::class)
    suspend fun update(business: Business)

    @Delete
    suspend fun delete(business: Business)

}