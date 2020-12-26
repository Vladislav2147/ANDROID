package by.bstu.vs.stpms.daytracker.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import by.bstu.vs.stpms.daytracker.model.entity.Business
import kotlinx.coroutines.flow.Flow


@Dao
interface BusinessDao {

    @Insert
    suspend fun insert(business: Business)

    @Query("SELECT * FROM business")
    fun getAll(): Flow<List<Business>>

    @Update
    suspend fun update(business: Business)

    @Delete
    suspend fun delete(business: Business)

}