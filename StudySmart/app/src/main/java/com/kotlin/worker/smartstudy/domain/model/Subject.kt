package com.kotlin.worker.smartstudy.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kotlin.worker.smartstudy.presentation.theme.gradient1
import com.kotlin.worker.smartstudy.presentation.theme.gradient2
import com.kotlin.worker.smartstudy.presentation.theme.gradient3
import com.kotlin.worker.smartstudy.presentation.theme.gradient4
import com.kotlin.worker.smartstudy.presentation.theme.gradient5

@Entity
data class Subject(
    val name: String,
    val goalHours: Float,
    val colours: List<Int>,
    @PrimaryKey(autoGenerate = true)
    val subjectId: Int? = null
) {
    companion object {
        val subjectCardColors = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
