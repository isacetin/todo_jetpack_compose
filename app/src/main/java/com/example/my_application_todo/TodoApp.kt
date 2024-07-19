package com.example.my_application_todo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TodoApp : Application() {

    /*
    @Inject
    lateinit var repository: TodoRepository
    */

    override fun onCreate() {
        super.onCreate()
        println("HelloWorld")
    }
}