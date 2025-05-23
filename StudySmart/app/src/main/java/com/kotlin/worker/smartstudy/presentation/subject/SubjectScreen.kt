package com.kotlin.worker.smartstudy.presentation.subject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kotlin.worker.smartstudy.SnackBarEvent
import com.kotlin.worker.smartstudy.domain.model.Task
import com.kotlin.worker.smartstudy.presentation.component.AddSubjectDialog
import com.kotlin.worker.smartstudy.presentation.component.CountCard
import com.kotlin.worker.smartstudy.presentation.component.DeleteDialog
import com.kotlin.worker.smartstudy.presentation.component.StudySessions
import com.kotlin.worker.smartstudy.presentation.component.taskList
import com.kotlin.worker.smartstudy.presentation.destinations.TaskScreenRouteDestination
import com.kotlin.worker.smartstudy.presentation.task.TaskScreenNavArgs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

data class SubjectScreenNavArgs(
    val subjectId: Int
)

@Destination(navArgsDelegate = SubjectScreenNavArgs::class)
@Composable
fun SubjectScreenRoute(navigator: DestinationsNavigator) {

    val viewModel: SubjectViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SubjectScreen(
        state = state,
        onEvent = viewModel::onEvent,
        snackbarEvent = viewModel.snackBarEventFlow,
        onBackClick = { navigator.navigateUp() },
        onAddTaskButtonClick = {
            val navArgs = TaskScreenNavArgs(taskId = null, subjectId = state.currentSubjectId)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArgs))
        },
        onTaskCardClick = { task ->
            val navArgs = TaskScreenNavArgs(taskId = task?.taskId, subjectId = state.currentSubjectId)
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArgs))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubjectScreen(
    state: SubjectState,
    onEvent: (SubjectEvent) -> Unit,
    snackbarEvent: SharedFlow<SnackBarEvent>,
    onBackClick: () -> Unit,
    onAddTaskButtonClick: () -> Unit,
    onTaskCardClick: (Task?) -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val isFabExpended by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }


    var isEditDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        snackbarEvent.collectLatest { event ->
            when (event) {
                is SnackBarEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }

                SnackBarEvent.NavigateUp -> onBackClick()
            }
        }
    }

    LaunchedEffect(key1 = state.studiedHours, key2 = state.goalStudyHours) {
        onEvent(SubjectEvent.UpdateProgress)
    }
    AddSubjectDialog(
        isOpen = isEditDialogOpen,
        subjectName = state.subjectName,
        selectedColor = state.subjectCardColor,
        subjectGoal = state.goalStudyHours,
        onColorChange = { onEvent(SubjectEvent.OnSubjectCardColorChange(color = it)) },
        onSubjectNameChange = { onEvent(SubjectEvent.OnSubjectNameChange(name = it)) },
        onSubjectGoalChange = { onEvent(SubjectEvent.OnGoalStudyHoursChange(hours = it)) },
        onConfirmButtonClick = {
            onEvent(SubjectEvent.UpdateSubject)
            isEditDialogOpen = false
        },
        onDismissRequest = { isEditDialogOpen = false },
    )
    DeleteDialog(
        title = "Delete Session?",
        isOpen = isDeleteDialogOpen,
        bodyText = "Are you sure you want to delete this session? Your studied hours will be reduced by this session time.",
        onConfirmButtonClick = {
            onEvent(SubjectEvent.DeleteSession)
            isDeleteDialogOpen = false
        },
        onDismissRequest = { isDeleteDialogOpen = false }
    )
    DeleteDialog(
        title = "Delete Subject?",
        isOpen = isDeleteSubjectDialogOpen,
        bodyText = "Are you sure you want to delete this Subject? \n all subject related Your studies and task will be permanently lost.",
        onConfirmButtonClick = {
            onEvent(SubjectEvent.DeleteSubject)
            isDeleteSubjectDialogOpen = false

        },
        onDismissRequest = { isDeleteSubjectDialogOpen = false }
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopBar(
                modifier = Modifier,
                title = state.subjectName,
                onBackClick = onBackClick,
                onDeleteSubjectClick = { isDeleteSubjectDialogOpen = true },
                onEditSubjectClick = { isEditDialogOpen = true },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddTaskButtonClick,
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "") },
                text = { Text(text = "Add Task") },
                expanded = isFabExpended
            )
        }
    ) { paddingValue ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            item {
                SubjectOverviewSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    studiedHour = state.studiedHours.toString(),
                    goalHours = state.goalStudyHours,
                    progress = state.progress
                )
            }
            taskList(
                sectionTitle = "UPCOMING TASKS",
                tasks = state.upcomingTasks,
                onCheckBoxClick = { onEvent(SubjectEvent.OnTaskIsCompleteChange(it)) },
                onClick = onTaskCardClick,
                emptyListText = "No more Tasks",
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            taskList(
                sectionTitle = "COMPLETED TASKS",
                tasks = state.completedTasks,
                onCheckBoxClick = { onEvent(SubjectEvent.OnTaskIsCompleteChange(it)) },
                onClick = onTaskCardClick,
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            StudySessions(
                sectionTitle = "RECENT STUDY SESSIONS",
                sessions = state.recentSessions,
                onDeleteIconClick = {
                    onEvent(SubjectEvent.OnDeleteSessionButtonClick(it))
                    isDeleteDialogOpen = true
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClick: () -> Unit,
    onDeleteSubjectClick: () -> Unit,
    onEditSubjectClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back"
                )
            }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )
        },

        modifier = Modifier,
        actions = {
            IconButton(onClick = onDeleteSubjectClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete subject"
                )
            }
            IconButton(onClick = onEditSubjectClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Subject"
                )
            }
        }
    )
}

@Composable
fun SubjectOverviewSection(
    modifier: Modifier = Modifier,
    studiedHour: String,
    goalHours: String,
    progress: Float
) {
    val percentageProgress = remember(progress) {
        (progress * 100).toInt().coerceIn(0, 100)
    }
    Row(
        modifier = modifier.padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            value = goalHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Study Hours",
            value = studiedHour
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.fillMaxSize(),
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant,
            )
            CircularProgressIndicator(
                progress = { progress },
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize(),
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
            )
            Text(text = "$percentageProgress%")
        }
    }
}