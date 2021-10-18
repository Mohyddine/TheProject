package com.mehyo.theproject.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mehyo.theproject.repo.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DataStoreViewModel(private val dataStoreRepository: DataStoreRepository): ViewModel() {

    val readLoginFromDataStore=dataStoreRepository.readLoginFromDataStore.asLiveData()

    fun saveLoginToDataStore(isLoggedIn:Boolean)=viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveLoginToDataStore(isLoggedIn)
    }

    val readTimerBaseFromDataStore=dataStoreRepository.readTimerBaseFromDataStore.asLiveData()

    fun saveTimerBaseToDataStore(TimerBase:Long)=viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveTimerBaseToDataStore(TimerBase)
    }

    val readFirstRunFromDataStore=dataStoreRepository.readFirstRunFromDataStore

    fun saveFirstRunToDataStore(isFirstRun:Boolean)=viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveFirstRunToDataStore(isFirstRun)
    }

    val readFirstRunDateFromDataStore=dataStoreRepository.readFirstRunDateFromDataStore.asLiveData()

    fun saveFirstRunDateToDataStore(FirstRunDate:String)=viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveFirstRunDateToDataStore(FirstRunDate)
    }

    fun clearDataStore()=viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.clearDataStore()
    }

}