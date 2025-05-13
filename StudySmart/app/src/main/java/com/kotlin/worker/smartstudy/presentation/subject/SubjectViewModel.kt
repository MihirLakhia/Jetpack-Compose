package com.kotlin.worker.smartstudy.presentation.subject

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.worker.smartstudy.SnackBarEvent
import com.kotlin.worker.smartstudy.domain.model.Subject
import com.kotlin.worker.smartstudy.domain.model.Task
import com.kotlin.worker.smartstudy.domain.repository.SessionRepository
import com.kotlin.worker.smartstudy.domain.repository.SubjectRepository
import com.kotlin.worker.smartstudy.domain.repository.TaskRepository
import com.kotlin.worker.smartstudy.presentation.navArgs
import com.kotlin.worker.smartstudy.toHour
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
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val sessionRepository: SessionRepository,
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs: SubjectScreenNavArgs = savedStateHandle.navArgs()

    private val _state = MutableStateFlow(SubjectState())
    val state = combine(
        _state,
        taskRepository.getUpcomingTasksForSubject(subjectId = navArgs.subjectId),
        taskRepository.getCompletedTasksForSubject(subjectId = navArgs.subjectId),
        sessionRepository.getRecentTenSessionForSubject(subjectId = navArgs.subjectId),
        sessionRepository.getTotalSessionDurationBySubject(subjectId = navArgs.subjectId)
    ) { state, upcomingTasks, completedTasks, recentSessions, totalSessionsDuration ->
        state.copy(
            upcomingTasks = upcomingTasks,
            completedTasks = completedTasks,
            recentSessions = recentSessions,
            studiedHours = totalSessionsDuration.toHour()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = SubjectState()
    )
    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    init {
        fetchSubjects()
    }

    fun onEvent(event: SubjectEvent) {
        when (event) {
            is SubjectEvent.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(subjectCardColor = event.color)
                }
            }

            is SubjectEvent.OnSubjectNameChange -> {
                _state.update {
                    it.copy(subjectName = event.name)
                }
            }

            is SubjectEvent.OnGoalStudyHoursChange -> {
                _state.update {
                    it.copy(goalStudyHours = event.hours)
                }
            }

            SubjectEvent.UpdateSubject -> updateSubject()
            SubjectEvent.DeleteSubject -> deleteSubjects()

            SubjectEvent.DeleteSession -> {}
            is SubjectEvent.OnDeleteSessionButtonClick -> {}
            is SubjectEvent.OnTaskIsCompleteChange -> updateTask(event.task)

            SubjectEvent.UpdateProgress -> {
                val goalStudyHour = state.value.goalStudyHours.toFloatOrNull() ?: 1f
                _state.update {
                    it.copy(
                        progress = (state.value.studiedHours / goalStudyHour).coerceIn(0f, 1f)
                    )
                }


            }
        }
    }

    private fun updateSubject() {
        viewModelScope.launch {
            try {
                subjectRepository.upsertSubject(
                    subject = Subject(
                        subjectId = state.value.currentSubjectId,
                        name = state.value.subjectName,
                        goalHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                        colours = state.value.subjectCardColor.map { it.toArgb() }
                    )
                )
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar("Subject updated successfully.")
                )
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't update subject. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }
    }


    private fun fetchSubjects() {
        viewModelScope.launch {
            subjectRepository.getSubjectById(navArgs.subjectId)?.let { subject ->

                _state.update {
                    it.copy(
                        subjectName = subject.name,
                        goalStudyHours = subject.goalHours.toString(),
                        subjectCardColor = subject.colours.map { Color(it) },
                        currentSubjectId = subject.subjectId
                    )
                }

            }
        }
    }

    private fun deleteSubjects() {
        viewModelScope.launch {
            try {
                val currentSubjectId = state.value.currentSubjectId
                if (currentSubjectId != null) {
                    withContext(Dispatchers.IO) {
                        subjectRepository.deleteSubject(subjectId = currentSubjectId)
                    }
                    _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar("Subject deleted successfully."))
                    _snackBarEventFlow.emit(SnackBarEvent.NavigateUp)
                } else {
                    _snackBarEventFlow.emit(SnackBarEvent.ShowSnackBar("No subject to delete."))
                }
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't delete Subject. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }

        }
    }
    private fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.upsertTask(
                    task = task.copy(isCompleted = !task.isCompleted)
                )
                val message = if(task.isCompleted)"upcoming" else "completed"
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar("Task saved in $message list.")
                )
            }catch (e:Exception){
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar("Couldn't save task. ${e.message}", SnackbarDuration.Long)
                )
            }
        }


    }
}