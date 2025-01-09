package com.example.heytodo.modal.repository

import com.example.heytodo.modal.dao.TaskDao
import com.example.heytodo.modal.entity.Tasks

class TaskRepository(private val taskDao: TaskDao) {
    suspend fun insertTask(tasks: Tasks) {
        taskDao.insertTask(tasks)
    }

    suspend fun updateTask(tasks: Tasks) {
        taskDao.updateTask(tasks)
    }

    suspend fun deleteTask(tasks: Tasks) {
        taskDao.deleteTask(tasks)
    }

    suspend fun getAllTasks(): List<Tasks> {
        return taskDao.getAllTask()
    }
}