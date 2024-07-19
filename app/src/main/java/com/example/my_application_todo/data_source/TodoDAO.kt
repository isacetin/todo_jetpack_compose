package com.example.my_application_todo.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAO {

    @Query("SELECT * FROM TodoEntity WHERE id = :id")
    fun getTodoById(id: Int): Flow<TodoEntity>

    @Query("SELECT * FROM TodoEntity")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Update(entity = TodoEntity::class)
    fun updateTodoItem(vararg todos: TodoEntity)

    @Insert(entity = TodoEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertTodoItem(todoEntity: TodoEntity)
}