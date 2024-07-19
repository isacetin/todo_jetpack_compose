package com.example.my_application_todo.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TodoEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao: TodoDAO
}