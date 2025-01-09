package com.example.heytodo.modal.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.heytodo.modal.entity.Tasks

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(tasks: Tasks)

    @Update
    suspend fun updateTask(tasks: Tasks)

    @Delete
    suspend fun deleteTask(tasks: Tasks)

    @Query("select * from tasks")
    suspend fun getAllTask() : List<Tasks>
}