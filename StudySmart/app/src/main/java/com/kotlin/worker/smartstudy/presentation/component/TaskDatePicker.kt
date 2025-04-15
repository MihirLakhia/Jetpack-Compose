package com.kotlin.worker.smartstudy.presentation.component

import android.annotation.SuppressLint
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDatePicker(
    state: DatePickerState,
    isOpen: Boolean,
    dismissButtonText: String = "Cancel",
    confirmButtonText: String = "Ok",
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,

    ) {
    if (isOpen)
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = onConfirmButtonClick) {
                    Text(text = confirmButtonText)
                }
            },
            modifier = Modifier,
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = dismissButtonText)
                }
            },
            content = {
                DatePicker(
                    state = state,
                    /* dateValidator = { timestamp ->
                     val selectedDate = Instant.ofEpochMilli(timestamp)
                         .atZone(ZoneId.systemDefault())
                         .toLocalDate()
                     val currentDate = LocalDate.now(ZoneId.systemDefault())
                     selectedDate >= currentDate
                 }*/
                )
            }
        )
}