package com.kotlin.worker.smartstudy.presentation.dashboard

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.worker.smartstudy.SnackBarEvent
import com.kotlin.worker.smartstudy.domain.model.Session
import com.kotlin.worker.smartstudy.domain.model.Subject
import com.kotlin.worker.smartstudy.domain.model.Task
import com.kotlin.worker.smartstudy.domain.repository.SessionRepository
import com.kotlin.worker.smartstudy.domain.repository.SubjectRepository
import com.kotlin.worker.smartstudy.domain.repository.TaskRepository
import com.kotlin.worker.smartstudy.toHour
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository,
    private val sessionRepository: SessionRepository,
    private val taskRepository: TaskRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state = combine(
        _state,
        subjectRepository.getTotalSubjectCount(),
        subjectRepository.getTotalGoalHours(),
        subjectRepository.getAllSubject(),
        sessionRepository.getTotalSessionDuration()
    ){state, subjectCount, goalHours, subjects, totalSessionDuration ->
        state.copy(
            totalSubjectCount = subjectCount,
            totalGoalStudyHours = goalHours,
            subjects = subjects,
            totalStudiedHours = totalSessionDuration.toHour()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardState()
    )

    val tasks: StateFlow<List<Task>> = taskRepository.getAllUpComingTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val recentSession: StateFlow<List<Session>> = sessionRepository.getRecentFiveSession()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()
    fun onEvent(event:DashboardEvent){
        when(event){
            is DashboardEvent.OnSubjectNameChange ->{
                _state.update {
                    it.copy(subjectName =  event.name)
                }
            }
            is DashboardEvent.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy( session =  event.session)
                }
            }
            is DashboardEvent.OnGoalStudyHoursChange -> {
                _state.update {
                    it.copy(goalStudyHours = event.hours)
                }
            }
            is DashboardEvent.OnSubjectCardColorChange -> {
                _state.update {
                    it.copy(subjectCardColor = event.colors)
                }
            }
            is DashboardEvent.OnTaskIsCompleteChange -> TODO()
            DashboardEvent.SaveSubject -> saveSubject()
            DashboardEvent.DeleteSession -> TODO()
        }
    }

    private fun saveSubject() {
        viewModelScope.launch {
            try {

                subjectRepository.upsertSubject(
                    subject = Subject(
                        name = state.value.subjectName,
                        goalHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                        colours = state.value.subjectCardColor.map { it.toArgb() },
                    )
                )
                _state.update {
                    it.copy(
                        subjectName = "",
                        goalStudyHours = "",
                        subjectCardColor = Subject.subjectCardColors.random()
                    )
                }
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar("Subject saved successfully.")
                )
            }catch (e:Exception){
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar("Couldn't save subject. ${e.message}", SnackbarDuration.Long)
                )
            }
        }


    }
}