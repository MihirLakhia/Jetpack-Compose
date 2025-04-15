package com.kotlin.worker.smartstudy.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int,
    val reletedToSubject: String,
    val isCompleted: Boolean,
    @PrimaryKey(autoGenerate = true)
    val taskId: Int? = null,
    val taskSubjectId: Int

)
