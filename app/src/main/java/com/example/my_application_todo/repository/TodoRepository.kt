package com.example.my_application_todo.repository

import com.example.my_application_todo.data_source.TodoDAO
import com.example.my_application_todo.data_source.TodoEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TodoRepository {

    suspend fun getTodoById(id: Int): Flow<TodoEntity>

    suspend fun insertTodo(todoEntity: TodoEntity)

    suspend fun getAllTodos(): Flow<List<TodoEntity>>

    suspend fun updateTodoItem(todo: TodoEntity)
}

class TodoRepositoryImpl @Inject constructor(
    private val dao: TodoDAO
) : TodoRepository {
    override suspend fun getTodoById(id: Int): Flow<TodoEntity> {
        return withContext(IO) {
            dao.getTodoById(id)
        }
    }

    override suspend fun insertTodo(todoEntity: TodoEntity) {
        return withContext(IO) {
            dao.insertTodoItem(todoEntity)
        }
    }

    override suspend fun getAllTodos(): Flow<List<TodoEntity>> {
        return withContext(IO) {
            dao.getAllTodos()
        }
    }

    override suspend fun updateTodoItem(todo: TodoEntity) {
        return withContext(IO) {
            dao.updateTodoItem(todo)
        }
    }
}