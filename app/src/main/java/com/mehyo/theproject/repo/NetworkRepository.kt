package com.mehyo.theproject.repo

import com.mehyo.theproject.network.TodoAPI
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext

class NetworkRepository(private val api:TodoAPI) {

    //Get Todos From API using coroutines
    suspend fun getTodosFromAPI() = withContext(Dispatchers.IO){
        api.getAPIResult()
    }
}