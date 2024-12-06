package com.example.kotlin_pr5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel: ViewModel() {
    private val _todo = MutableLiveData<String>()
    val todo: LiveData<String> = _todo
    private val _completed = MutableLiveData<String>()
    val completed: LiveData<String> = _completed

    fun updateTodos(newTodo: String, isCompleted: Boolean){
        _todo.value = newTodo
        if (isCompleted){
            _completed.value = "Task is completed."
        }
        else{
            _completed.value = "Task is not completed."
        }
    }
}