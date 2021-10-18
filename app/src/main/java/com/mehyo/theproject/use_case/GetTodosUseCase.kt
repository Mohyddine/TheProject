package com.mehyo.theproject.use_case

import com.mehyo.theproject.model.TodoItem
import com.mehyo.theproject.repo.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTodosUseCase(private val networkRepository: NetworkRepository) {

    //Get Todos From API and return the result body with flow
    operator fun invoke(): Flow<List<TodoItem>> = flow{
        val result= networkRepository.getTodosFromAPI()
        if (result.isSuccessful){
            if (result.body() != null) {
                emit(result.body()!!)
            }
        }
    }
}