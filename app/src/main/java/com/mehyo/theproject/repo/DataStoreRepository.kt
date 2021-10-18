package com.mehyo.theproject.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.mehyo.theproject.constant.Constants
import com.mehyo.theproject.repo.DataStoreRepository.PreferenceKeys.firstRun
import com.mehyo.theproject.repo.DataStoreRepository.PreferenceKeys.firstRunDate
import com.mehyo.theproject.repo.DataStoreRepository.PreferenceKeys.login
import com.mehyo.theproject.repo.DataStoreRepository.PreferenceKeys.timerBase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

class DataStoreRepository(private val context: Context){

    private object PreferenceKeys{
        val login = booleanPreferencesKey(Constants.LOGIN)
        val timerBase = longPreferencesKey(Constants.TIMER_BASE)
        val firstRunDate = stringPreferencesKey(Constants.FIRST_RUN_DATE)
        val firstRun = booleanPreferencesKey(Constants.FIRST_RUN)
    }

    private val Context.dataStore:DataStore<Preferences> by preferencesDataStore(
        name = Constants.DATA_STORE_PREFERENCE_NAME
    )

    //clear DataStore data using in logout
    suspend fun clearDataStore(){
        context.dataStore.edit {
            it.clear()
        }
    }

    suspend fun saveLoginToDataStore(isLoggedIn:Boolean){
        context.dataStore.edit {
            it[login]=isLoggedIn
        }
    }

    suspend fun saveTimerBaseToDataStore(TimerBase:Long){
        context.dataStore.edit {
            it[timerBase]=TimerBase
        }
    }

    suspend fun saveFirstRunToDataStore(isFirstRun:Boolean){
        context.dataStore.edit {
            it[firstRun]=isFirstRun
        }
    }

    suspend fun saveFirstRunDateToDataStore(FirstRunDate:String){
        context.dataStore.edit {
            it[firstRunDate]=FirstRunDate
        }
    }

    val readLoginFromDataStore:Flow<Boolean> = context.dataStore.data.map {
            it[login] ?: false
        }

    val readTimerBaseFromDataStore:Flow<Long> = context.dataStore.data.map {
            it[timerBase] ?: 0
        }

    val readFirstRunDateFromDataStore:Flow<String> = context.dataStore.data.map {
            it[firstRunDate] ?: SimpleDateFormat("dd/MM/yyyy hh:mm").format(Date())
        }

    val readFirstRunFromDataStore:Flow<Boolean> = context.dataStore.data.map {
            it[firstRun] ?: true
        }

}