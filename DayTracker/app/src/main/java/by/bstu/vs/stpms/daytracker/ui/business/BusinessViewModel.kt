package by.bstu.vs.stpms.daytracker.ui.business

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.*
import by.bstu.vs.stpms.daytracker.model.repository.BusinessRepository
import by.bstu.vs.stpms.daytracker.model.entity.Business
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch


class BusinessViewModel(application: Application) : AndroidViewModel(application) {

    private val businessRepository: BusinessRepository = BusinessRepository(application)
    val businessesLiveData = businessRepository.allBusinesses.asLiveData()
    lateinit var currentBusiness: LiveData<Business>

    fun insert(business: Business, onError: (e: SQLiteConstraintException)->Unit, onSuccess: ()->Unit) = viewModelScope.launch() {
        try {
            businessRepository.insert(business)
            onSuccess.invoke()
        } catch (e: SQLiteConstraintException) {
            onError.invoke(e)
        }
    }

    fun delete(business: Business) = viewModelScope.launch {
        businessRepository.delete(business)
    }

    fun update(business: Business) = viewModelScope.launch {
        businessRepository.update(business)
    }
}
