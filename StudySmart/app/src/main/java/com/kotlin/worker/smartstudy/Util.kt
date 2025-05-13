package com.kotlin.worker.smartstudy

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


enum class Priority(val title: String, val color: Color, val value: Int) {
    LOW(title = "Low", color = Color.Cyan, value = 1),
    MEDIUM(title = "Medium", color = Color.Magenta, value = 2),
    High("High", color = Color.Red, value = 3);

    companion object {
        fun fromInt(value: Int) = entries.firstOrNull { it.value == value } ?: MEDIUM
    }
}

fun Long?.changeMillisToDateString(): String {
    Log.d("Time", "$this")
    val date: LocalDate = this?.let { it: Long ->
        Instant
            .ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    } ?: LocalDate.now()
    return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}

fun Long.toHour(): Float {
    val hours = this.toFloat() / 360f
    return "%.2f".format(hours).toFloat()
}

sealed class SnackBarEvent {
    data class ShowSnackBar(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ) : SnackBarEvent()

    data object NavigateUp: SnackBarEvent()
}