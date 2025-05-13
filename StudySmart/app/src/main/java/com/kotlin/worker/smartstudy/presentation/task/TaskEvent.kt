package com.kotlin.worker.smartstudy.presentation.task

import com.kotlin.worker.smartstudy.Priority
import com.kotlin.worker.smartstudy.domain.model.Subject

sealed class TaskEvent(){

    data class OnTitleChange(val title: String) :TaskEvent()

    data class OnDescriptionChange(val description: String) :TaskEvent()

    data class OnDueDateChange(val millis: Long?) :TaskEvent()

    data class OnPriorityChange(val priority: Priority) :TaskEvent()

    data class OnRelatedSubjectSelect(val subject: Subject) :TaskEvent()

    data object OnIsCompleteChange:TaskEvent()

    data object SaveTask:TaskEvent()

    data object DeleteTask:TaskEvent()


}
