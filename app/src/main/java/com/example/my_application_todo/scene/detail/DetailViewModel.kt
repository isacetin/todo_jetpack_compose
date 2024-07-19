package com.example.my_application_todo.scene.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_application_todo.data_source.TodoEntity
import com.example.my_application_todo.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {


    private val _todo = MutableStateFlow<TodoEntity?>(null)
    val todo = _todo.asStateFlow()


    fun getTodoById(id: Int) {
        viewModelScope.launch {
            repository.getTodoById(id + 1).collect {
                _todo.tryEmit(it)
            }
        }
    }

    fun updateTodo(todoEntity: TodoEntity) {
        viewModelScope.launch {
            repository.updateTodoItem(todoEntity)
        }
    }

    fun insertTodo(content: String) {
        viewModelScope.launch {
            repository.insertTodo(TodoEntity(content = content))
        }
    }
}