package com.mehyo.theproject.network

import com.mehyo.theproject.model.TodoItem
import retrofit2.Response
import retrofit2.http.GET

interface TodoAPI {

    //suspending function for GET network calls
    @GET("todos")
    suspend fun getAPIResult(): Response<List<TodoItem>>
}