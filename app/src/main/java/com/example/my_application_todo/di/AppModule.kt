package com.example.my_application_todo.di

import android.app.Application
import androidx.room.Room
import com.example.my_application_todo.data_source.AppDatabase
import com.example.my_application_todo.repository.TodoRepository
import com.example.my_application_todo.repository.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "AppDatabase").build()
    }

    @Provides
    @Singleton
    fun provideMyRepository(db: AppDatabase): TodoRepository {
        return TodoRepositoryImpl(db.dao)
    }
}