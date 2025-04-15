package com.kotlin.worker.smartstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kotlin.worker.smartstudy.domain.model.Subject

@Composable
fun AddSubjectDialog(
    title: String = "Add / Update Subject",
    isOpen: Boolean,
    selectedColor: List<Color>,
    onColorChange: (List<Color>) -> Unit,
    subjectName: String,
    onSubjectNameChange: (String) -> Unit,
    subjectGoal: String,
    onSubjectGoalChange: (String) -> Unit,
    onConfirmButtonClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    var goalError by rememberSaveable { mutableStateOf<String?>(null) }
    var nameError by rememberSaveable { mutableStateOf<String?>(null) }

    nameError = when {
        subjectName.isBlank() -> "Please enter subject name."
        subjectName.length < 3 -> "subject name too small."
        subjectName.length > 12 -> "subject name too big, only 12 character."
        else -> null
    }
    goalError = when {
        subjectGoal.isBlank() -> "Please enter Goal hour."
        subjectGoal.toFloatOrNull() == null -> "Invalid Number."
        subjectGoal.toFloat() < 1f -> "please set at least 1 hour."
        subjectGoal.toFloat() > 1000f -> "please set a maximum of 1000 hours"

        else -> null
    }

    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = title) },
            text = {
                DialogBody(
                    selectedColor = selectedColor,
                    onColorChange = onColorChange,
                    subjectName = subjectName,
                    onSubjectNameChange = onSubjectNameChange,
                    subjectGoal = subjectGoal,
                    onSubjectGoalChange = onSubjectGoalChange,
                    nameError = nameError,
                    goalError = goalError
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick,
                    enabled = nameError.isNullOrBlank() && goalError.isNullOrBlank()
                ) {
                    Text(
                        text = "Save",
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
            },
        )

    }
}

@Composable
fun DialogBody(
    selectedColor: List<Color>,
    onColorChange: (List<Color>) -> Unit,
    subjectName: String,
    onSubjectNameChange: (String) -> Unit,
    subjectGoal: String,
    onSubjectGoalChange: (String) -> Unit,
    nameError: String?,
    goalError: String?
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Subject.subjectCardColors.forEach { colors ->
                Box(modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = if (colors == selectedColor) Color.Black else Color.Transparent,
                        shape = CircleShape
                    )
                    .background(brush = Brush.verticalGradient(colors))
                    .clickable {
                        onColorChange(colors)
                    }
                )
            }
        }

        OutlinedTextField(
            value = subjectName,
            onValueChange = onSubjectNameChange,
            label = { Text(text = "Subject Name") },
            singleLine = true,
            isError = nameError.isNullOrBlank(),
            supportingText = { Text(text = nameError.orEmpty()) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = subjectGoal,
            onValueChange = onSubjectGoalChange,
            label = { Text(text = "Goal Study Hours") },
            singleLine = true,
            isError = goalError.isNullOrBlank(),
            supportingText = { Text(text = goalError.orEmpty()) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }

}