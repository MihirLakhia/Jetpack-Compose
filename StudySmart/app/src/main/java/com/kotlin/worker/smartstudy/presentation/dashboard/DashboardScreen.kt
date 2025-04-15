package com.kotlin.worker.smartstudy.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kotlin.worker.smartstudy.R
import com.kotlin.worker.smartstudy.domain.model.Subject
import com.kotlin.worker.smartstudy.presentation.component.AddSubjectDialog
import com.kotlin.worker.smartstudy.presentation.component.CountCard
import com.kotlin.worker.smartstudy.presentation.component.DeleteDialog
import com.kotlin.worker.smartstudy.presentation.component.EmptyListIcon
import com.kotlin.worker.smartstudy.presentation.component.StudySessions
import com.kotlin.worker.smartstudy.presentation.component.SubjectCard
import com.kotlin.worker.smartstudy.presentation.component.taskList
import com.kotlin.worker.smartstudy.presentation.destinations.SessionScreenRouteDestination
import com.kotlin.worker.smartstudy.presentation.destinations.SubjectScreenRouteDestination
import com.kotlin.worker.smartstudy.presentation.destinations.TaskScreenRouteDestination
import com.kotlin.worker.smartstudy.presentation.subject.SubjectScreenNavArgs
import com.kotlin.worker.smartstudy.presentation.task.TaskScreenNavArgs
import com.kotlin.worker.smartstudy.sessions
import com.kotlin.worker.smartstudy.subjects
import com.kotlin.worker.smartstudy.tasks
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun DashboardScreenRoute(
    navigator: DestinationsNavigator
) {

    DashboardScreen(
        onSubjectCardClick = { subjectId ->
            subjectId?.let {
                val navArgs = SubjectScreenNavArgs(subjectId = subjectId)
                navigator.navigate(SubjectScreenRouteDestination(navArgs = navArgs))
            }
        },
        onTaskCardClick = { taskId ->

            val navArgs = TaskScreenNavArgs(
                taskId = taskId,
                subjectId = null
            )
            navigator.navigate(TaskScreenRouteDestination(navArgs = navArgs))
        },
        onStartSessionClick = {
            navigator.navigate(SessionScreenRouteDestination())
        }
    )
}

@Composable
private fun DashboardScreen(
    onSubjectCardClick: (Int?) -> Unit,
    onTaskCardClick: (Int?) -> Unit,
    onStartSessionClick: () -> Unit
) {


    var isAddDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }
    var subjectName by remember { mutableStateOf("") }
    var subjectGoal by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Subject.subjectCardColors.random()) }
    AddSubjectDialog(
        isOpen = isAddDialogOpen,
        onConfirmButtonClick = { isAddDialogOpen = false },
        onDismissRequest = { isAddDialogOpen = false },
        selectedColor = selectedColor,
        onColorChange = { selectedColor = it },
        subjectName = subjectName,
        onSubjectNameChange = { subjectName = it },
        subjectGoal = subjectGoal,
        onSubjectGoalChange = { subjectGoal = it },
    )
    DeleteDialog(
        title = "Delete Session?",
        isOpen = isDeleteDialogOpen,
        bodyText = "Are you sure you want to delete this session? Your studied hours will be reduced by this session time.",
        onConfirmButtonClick = { isDeleteDialogOpen = false },
        onDismissRequest = { isDeleteDialogOpen = false }
    )
    Scaffold(
        topBar = { DashboardTopBar() }
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            item {
                CountBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    subjectCount = 5,
                    studiedHour = "10",
                    goalHour = "15"
                )
            }
            item {
                SubjectCardSection(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = subjects,//emptyList(),
                    onAddIconClick = { isAddDialogOpen = true },
                    onSubjectCardClick = onSubjectCardClick
                )
            }

            item {
                Button(
                    onClick = { onStartSessionClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ) {
                    Text(text = "Start Study Session")
                }
            }

            taskList(
                sectionTitle = "Upcoming Tasks",
                tasks = tasks,
                onCheckBoxClick = {},
                onClick = onTaskCardClick,

                )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            StudySessions(
                sectionTitle = "RECENT STUDY SESSIONS",
                sessions = sessions,
                onDeleteIconClick = { isDeleteDialogOpen = true }
            )
        }
    }
}

@Composable
private fun CountBar(
    modifier: Modifier = Modifier,
    subjectCount: Int,
    studiedHour: String,
    goalHour: String
) {

    Row(modifier = modifier) {
        CountCard(
            modifier = modifier.weight(1f),
            headingText = "Subject Count",
            value = "$subjectCount"
        )
        Spacer(modifier = Modifier.width(5.dp))
        CountCard(
            modifier = modifier.weight(1f),
            headingText = "Studied Hours",
            value = studiedHour
        )
        Spacer(modifier = Modifier.width(5.dp))
        CountCard(
            modifier = modifier.weight(1f),
            headingText = "Goal Study Hours",
            value = goalHour
        )

    }

}

@Composable
fun SubjectCardSection(
    modifier: Modifier = Modifier,
    subjectList: List<Subject> = emptyList(),
    emptyListText: String = "You don't have any subject.\n Click the + button to add new Subject.",
    onAddIconClick: () -> Unit,
    onSubjectCardClick: (Int?) -> Unit
) {
    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Subjects",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick = onAddIconClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Subject"
                )
            }
        }

        if (subjectList.isEmpty()) {
            EmptyListIcon(
                image = painterResource(R.drawable.img_books),
                emptyListText = emptyListText
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) { subject ->
                SubjectCard(subjectName = subject.name,
                    gradientColor = subject.colours,
                    onClick = { onSubjectCardClick(subject.subjectId) }
                )
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardTopBar() {

    CenterAlignedTopAppBar(
        title = {
            Text(
                "SmartStudy",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    )

}