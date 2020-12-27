package by.bstu.vs.stpms.daytracker.ui.business

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import by.bstu.vs.stpms.daytracker.model.entity.Business
import by.bstu.vs.stpms.daytracker.model.repository.BusinessRepository
import kotlinx.coroutines.launch


class BusinessViewModel(application: Application) : AndroidViewModel(application) {

    private val businessRepository: BusinessRepository = BusinessRepository(application)
    val businessesLiveData = businessRepository.allBusinesses.asLiveData()
    private var createMode = true
    lateinit var currentBusiness: MutableLiveData<Business>

    fun setLiveData(business: Business) {
        createMode = business.id == null
        this.currentBusiness = MutableLiveData(business)
    }

    fun setCurrent(business: Business) {
        currentBusiness.setValue(business)
    }

    fun save(
        onError: (e: SQLiteConstraintException) -> Unit,
        onSuccess: () -> Unit
    ) = viewModelScope.launch() {
        try {
            if(createMode) businessRepository.insert(currentBusiness.value!!)
            else businessRepository.update(currentBusiness.value!!)
            onSuccess.invoke()
        } catch (e: SQLiteConstraintException) {
            onError.invoke(e)
        }
    }

    fun delete(business: Business) = viewModelScope.launch {
        businessRepository.delete(business)
    }

}
