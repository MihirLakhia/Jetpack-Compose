package com.kotlin.worker.smartstudy.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.worker.smartstudy.Priority
import com.kotlin.worker.smartstudy.changeMillisToString
import com.kotlin.worker.smartstudy.presentation.component.DeleteDialog
import com.kotlin.worker.smartstudy.presentation.component.SubjectListBottomSheet
import com.kotlin.worker.smartstudy.presentation.component.TaskCheckBox
import com.kotlin.worker.smartstudy.presentation.component.TaskDatePicker
import com.kotlin.worker.smartstudy.subjects
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant

data class TaskScreenNavArgs(
    val taskId: Int?,
    val subjectId: Int?
)


@Destination(navArgsDelegate = TaskScreenNavArgs::class)
@Composable
fun TaskScreenRoute(navigator: DestinationsNavigator) {

    val viewModel:TaskViewModel = hiltViewModel()

    TaskScreen(
        onBackButtonClick = { navigator.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreen(
    onBackButtonClick: () -> Unit
) {

    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("12 june") }
    val taskPriority by remember { mutableIntStateOf(1) }
    var titleError by rememberSaveable { mutableStateOf<String?>(null) }


    titleError = when {
        taskTitle.isBlank() -> "Please enter task title."
        taskTitle.length < 3 -> "Task title too small."
        taskTitle.length > 12 -> "Task title too big, only 12 character."
        else -> null
    }

    var isDeleteDialogOpen by rememberSaveable { mutableStateOf(false) }
    DeleteDialog(
        title = "Delete Task?",
        isOpen = isDeleteDialogOpen,
        bodyText = "Are you sure you want to delete this Task? ",
        onConfirmButtonClick = { isDeleteDialogOpen = false },
        onDismissRequest = { isDeleteDialogOpen = false }
    )
    var isDatePickerDialogOpen by rememberSaveable { mutableStateOf(false) }
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = Instant.now().toEpochMilli())
    TaskDatePicker(
        state = datePickerState,
        isOpen = isDatePickerDialogOpen,
        onDismissRequest = { isDatePickerDialogOpen = false },
        onConfirmButtonClick = { isDatePickerDialogOpen = false })

    val sheetState = rememberModalBottomSheetState()
    var isBottomSheetOpen by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    SubjectListBottomSheet(
        sheetState = sheetState,
        isOpen = isBottomSheetOpen,
        subjects = subjects,
        bottomSheetTitle = "Select Subject",
        onSubjectClicked = {
            // isBottomSheetOpen = false
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible)
                    isBottomSheetOpen = false
            }
        },
        onDismissRequest = { isBottomSheetOpen = false }
    )


    Scaffold(
        topBar = {
            TaskTopBar(
                modifier = Modifier,
                isTaskExist = true,
                isCompleted = false,
                checkBoxBorderColor = Color.Red,
                onBackButtonClick = onBackButtonClick ,
                onDeleteButtonClick = { isDeleteDialogOpen = true },
                onCheckBoxClick = { }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(state = rememberScrollState())
                .padding(horizontal = 12.dp)
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = taskTitle,
                onValueChange = { taskTitle = it },
                label = { Text(text = "Title") },
                singleLine = true,
                isError = titleError.isNullOrBlank(),
                supportingText = { Text(text = titleError.orEmpty()) },
            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = taskDescription,
                onValueChange = { taskDescription = it },
                label = { Text(text = "Description") },
                // maxLines = 5
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Due Date",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = datePickerState.selectedDateMillis.changeMillisToString(),
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(onClick = { isDatePickerDialogOpen = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "calender"
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Priority",
                style = MaterialTheme.typography.bodySmall
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Priority.entries.forEach { priority ->

                    PriorityButton(
                        modifier = Modifier.weight(1f),
                        label = priority.title,
                        backgroundColor = priority.color,
                        borderColor = if (taskPriority == priority.value) Color.White else Color.Transparent,
                        labelColor = Color.White,
                        onClick = { }
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Related to Subject",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "English ",
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(onClick = { isBottomSheetOpen = true }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "DropDown"
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                enabled = titleError == null,
                onClick = {},
            ) { Text(text = "Save") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopBar(
    modifier: Modifier = Modifier,
    isTaskExist: Boolean,
    isCompleted: Boolean,
    checkBoxBorderColor: Color,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onCheckBoxClick: () -> Unit,
) {

    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "back"
                )
            }
        },
        title = { Text(text = "Tasks") },
        actions = {
            if (isTaskExist) {
                TaskCheckBox(
                    isCompleted = isCompleted,
                    borderColor = checkBoxBorderColor,
                    onCheckClick = onCheckBoxClick
                )
                IconButton(onClick = onDeleteButtonClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }

        }
    )

}

@Composable
fun PriorityButton(
    modifier: Modifier = Modifier,
    label: String,
    backgroundColor: Color,
    borderColor: Color,
    labelColor: Color,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(5.dp)
            .border(1.dp, borderColor, RoundedCornerShape(5.dp))
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = labelColor)
    }
}