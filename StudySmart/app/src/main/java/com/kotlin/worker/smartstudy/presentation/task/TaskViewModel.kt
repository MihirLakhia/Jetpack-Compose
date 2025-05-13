package com.kotlin.worker.smartstudy.presentation.task

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.worker.smartstudy.Priority
import com.kotlin.worker.smartstudy.SnackBarEvent
import com.kotlin.worker.smartstudy.domain.model.Task
import com.kotlin.worker.smartstudy.domain.repository.SubjectRepository
import com.kotlin.worker.smartstudy.domain.repository.TaskRepository
import com.kotlin.worker.smartstudy.presentation.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

@HiltViewModel

class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val subjectRepository: SubjectRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs: TaskScreenNavArgs = savedStateHandle.navArgs()
    private val _state = MutableStateFlow(TaskState())

    val state = combine(
        _state,
        subjectRepository.getAllSubject()
    ) { state, subjects ->
        state.copy(
            subjects = subjects,
            relatedToSubject = subjects.filter { it.subjectId == navArgs.subjectId }
                .firstOrNull()?.name,
            subjectId = navArgs.subjectId
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = TaskState()
    )

    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    init {
        fetchTask()
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.OnTitleChange -> {
                _state.update { it.copy(title = event.title) }
            }

            is TaskEvent.OnDescriptionChange -> {
                _state.update { it.copy(description = event.description) }
            }

            is TaskEvent.OnDueDateChange -> {
                _state.update { it.copy(dueDate = event.millis) }
            }

            is TaskEvent.OnPriorityChange -> {
                _state.update { it.copy(priority = event.priority) }
            }

            TaskEvent.OnIsCompleteChange -> {
                _state.update { it.copy(isTaskComplete = !_state.value.isTaskComplete) }
            }

            is TaskEvent.OnRelatedSubjectSelect -> {
                _state.update {
                    it.copy(
                        relatedToSubject = event.subject.name,
                        subjectId = event.subject.subjectId
                    )
                }
            }

            TaskEvent.SaveTask -> saveTask()
            TaskEvent.DeleteTask -> deleteTask()
        }
    }

    private fun deleteTask() {
        viewModelScope.launch {
            try {
                val state = _state.value
                if (state.currentTaskId == null) {
                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            "No Task to Delete",
                            SnackbarDuration.Long
                        )
                    )
                    return@launch
                }
                withContext(Dispatchers.IO) {
                    taskRepository.deleteTask(state.currentTaskId)
                }
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar("Task deleted successfully.")
                )
                _snackBarEventFlow.emit(SnackBarEvent.NavigateUp)
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't delete task. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun saveTask() {
        viewModelScope.launch {
            try {
                val state = _state.value
                if (state.subjectId == null || state.relatedToSubject == null) {
                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            "Please select subject related to the task.",
                            SnackbarDuration.Long
                        )
                    )
                    return@launch
                }

                taskRepository.upsertTask(
                    task = Task(
                        title = state.title,
                        description = state.description,
                        dueDate = state.dueDate ?: Instant.now().toEpochMilli(),
                        priority = state.priority.value,
                        reletedToSubject = state.relatedToSubject,
                        isCompleted = state.isTaskComplete,
                        taskId = state.currentTaskId,
                        taskSubjectId = state.subjectId
                    )
                )
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar("Task saved successfully.")
                )
                _snackBarEventFlow.emit(SnackBarEvent.NavigateUp)
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't save task. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }

        }

    }

    private fun fetchTask() {

        viewModelScope.launch {

            navArgs.taskId?.let { id ->
                taskRepository.getTaskById(id)?.let { task ->
                    _state.update {
                        it.copy(
                            title = task.title,
                            description = task.description,
                            dueDate = task.dueDate,
                            isTaskComplete = task.isCompleted,
                            relatedToSubject = task.reletedToSubject,
                            priority = Priority.fromInt(task.priority),
                            subjectId = task.taskSubjectId,
                            currentTaskId = task.taskId
                        )
                    }
                }

            }
        }
    }

}