package com.kotlin.worker.smartstudy.presentation.task

import com.kotlin.worker.smartstudy.Priority
import com.kotlin.worker.smartstudy.domain.model.Subject

data class TaskState (

    val title :String = "",
    val description :String = "",
    val dueDate:Long? = null,
    val isTaskComplete: Boolean = false,
    val priority: Priority  = Priority.LOW,
    val relatedToSubject: String? ="",
    val subjects:List<Subject> = emptyList(),
    val subjectId: Int? = null,
    val currentTaskId: Int? = null
)