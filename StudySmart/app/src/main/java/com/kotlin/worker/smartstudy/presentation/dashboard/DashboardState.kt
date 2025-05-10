package com.kotlin.worker.smartstudy.presentation.dashboard

import androidx.compose.ui.graphics.Color
import com.kotlin.worker.smartstudy.domain.model.Session
import com.kotlin.worker.smartstudy.domain.model.Subject

data class DashboardState(
    val totalSubjectCount:Int = 0,
    val totalStudiedHours:Float = 0f,
    val totalGoalStudyHours: Float =0f,
    val subjects: List<Subject> = emptyList(),
    val subjectName: String ="",
    val goalStudyHours: String ="",
    val subjectCardColor:List<Color> = Subject.subjectCardColors.random(),
    val session: Session? = null
)