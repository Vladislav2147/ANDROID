package by.bstu.vs.stpms.daytracker.ui.business

import android.app.Application
import androidx.lifecycle.*
import by.bstu.vs.stpms.daytracker.model.BusinessRepository
import by.bstu.vs.stpms.daytracker.model.entity.Business
import kotlinx.coroutines.launch


class BusinessViewModel(application: Application) : AndroidViewModel(application) {

    private val businessRepository: BusinessRepository = BusinessRepository(application)
    val businessesLiveData = businessRepository.allBusinesses.asLiveData()

    fun insert(business: Business) = viewModelScope.launch {
        businessRepository.insert(business)
    }

    fun delete(business: Business) = viewModelScope.launch {
        businessRepository.delete(business)
    }

    fun update(business: Business) = viewModelScope.launch {
        businessRepository.update(business)
    }
}
