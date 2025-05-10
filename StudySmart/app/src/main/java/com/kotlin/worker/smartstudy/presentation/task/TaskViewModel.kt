package com.kotlin.worker.smartstudy.presentation.task

import androidx.lifecycle.ViewModel
import com.kotlin.worker.smartstudy.domain.repository.TaskRepository
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
):ViewModel() {
}