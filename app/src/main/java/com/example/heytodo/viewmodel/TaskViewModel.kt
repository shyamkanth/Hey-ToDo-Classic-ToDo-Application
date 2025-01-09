package com.example.heytodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heytodo.modal.entity.Tasks
import com.example.heytodo.modal.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    fun insertTask(task: Tasks) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

    fun updateTask(task: Tasks) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun deleteTask(task: Tasks) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    fun getAllTask() : LiveData<List<Tasks>> {
        val result = MutableLiveData<List<Tasks>>()
        viewModelScope.launch {
            result.postValue(taskRepository.getAllTasks())
        }
        return result
    }
}