package by.bstu.vs.stpms.daytracker.model

import android.app.Application
import by.bstu.vs.stpms.daytracker.model.dao.BusinessDao
import by.bstu.vs.stpms.daytracker.model.entity.Business

class BusinessRepository(application: Application) {

    private val businessDao: BusinessDao = BusinessDatabase.getDatabase(application).businessDao()

    val allBusinesses = businessDao.getAll()

    suspend fun insert(business: Business) {
        businessDao.insert(business)
    }

    suspend fun delete(business: Business) {
        businessDao.delete(business)
    }

    suspend fun update(business: Business) {
        businessDao.update(business)
    }
}