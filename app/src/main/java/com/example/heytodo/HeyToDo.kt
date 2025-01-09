package com.example.heytodo

import android.app.Application
import com.example.heytodo.modal.repository.TaskRepository
import com.example.heytodo.modal.AppDatabase

class HeyToDo : Application(){
    lateinit var database: AppDatabase
        private set
    lateinit var taskRepository: TaskRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getDatabase(this)
        taskRepository = TaskRepository(database.taskDao())
    }
}