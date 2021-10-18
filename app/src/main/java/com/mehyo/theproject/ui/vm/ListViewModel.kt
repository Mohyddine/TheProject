package com.mehyo.theproject.ui.vm

import androidx.lifecycle.*
import com.mehyo.theproject.model.TodoItem
import com.mehyo.theproject.use_case.GetTodosUseCase


class ListViewModel(private val getTodosUseCase: GetTodosUseCase): ViewModel() {

    init {
        getTodos()
    }

    private var todoAPIMutableLiveData=MutableLiveData<List<TodoItem>>()
    val todosAPILiveData:LiveData<List<TodoItem>> get() =todoAPIMutableLiveData

    //get Todos from useCase and put the list inside the MutableLiveData
    private fun getTodos(){
        getTodosUseCase.invoke().asLiveData().observeForever {list->
            todoAPIMutableLiveData.postValue(list)
        }
    }
}