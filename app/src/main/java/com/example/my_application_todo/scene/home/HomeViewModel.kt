package com.example.my_application_todo.scene.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_application_todo.data_source.TodoEntity
import com.example.my_application_todo.model.UiState
import com.example.my_application_todo.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.TimeSource

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    init {
        getAllTodos()
    }

    private val _todos = MutableStateFlow(emptyList<TodoEntity>())
    val todos = _todos.asStateFlow()


    private val _updatedTask = MutableStateFlow(TodoEntity())
    val updatedTask = _updatedTask.asStateFlow()

    fun getAllTodos() {
        viewModelScope.launch(IO) {
            repository.getAllTodos().collectLatest {
                _todos.tryEmit(it)
            }
        }
    }

    fun updateTodo(todoEntity: TodoEntity) {
        viewModelScope.launch {
            repository.updateTodoItem(todoEntity)
            getAllTodos()
            _updatedTask.emit(todoEntity)
        }
    }
}