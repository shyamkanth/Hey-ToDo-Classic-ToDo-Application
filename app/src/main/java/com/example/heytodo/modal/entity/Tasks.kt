package com.example.heytodo.modal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Tasks(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "task_name") val taskName: String,
    @ColumnInfo(name = "task_description") val taskDescription: String,
)
