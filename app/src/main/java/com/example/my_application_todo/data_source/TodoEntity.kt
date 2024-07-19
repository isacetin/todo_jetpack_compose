package com.example.my_application_todo.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = tableName)
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val content: String = "",
    val isCompleted: Boolean = false
)

const val tableName = "TodoEntity"

