package com.kotlin.worker.smartstudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.kotlin.worker.smartstudy.domain.model.Session
import com.kotlin.worker.smartstudy.domain.model.Subject
import com.kotlin.worker.smartstudy.domain.model.Task
import com.kotlin.worker.smartstudy.presentation.NavGraphs
import com.kotlin.worker.smartstudy.presentation.theme.SmartStudyTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartStudyTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)

            }
        }
    }
}

val subjects = listOf(
    Subject(
        name = "English",
        goalHours = 10f,
        colours = Subject.subjectCardColors[0].map { it.toArgb() },
        subjectId = 1
    ),
    Subject(
        name = "Physics",
        goalHours = 10f,
        colours = Subject.subjectCardColors[1].map { it.toArgb() },
        subjectId = 2
    ),
    Subject(
        name = "Math",
        goalHours = 10f,
        colours = Subject.subjectCardColors[2].map { it.toArgb() },
        subjectId = 3
    ),
    Subject(
        name = "Geology",
        goalHours = 10f,
        colours = Subject.subjectCardColors[3].map { it.toArgb() },
        subjectId = 4
    ),
    Subject(
        name = "Fine Arts",
        goalHours = 10f,
        colours = Subject.subjectCardColors[4].map { it.toArgb() },
        subjectId = 5
    )
)

val tasks = listOf(
    Task(
        title = "Prerpare Notes",
        description = "",
        dueDate = 0L,
        priority = 1,
        reletedToSubject = "",
        isCompleted = false,
        taskSubjectId = 1,
        taskId = 1
    ),
    Task(
        title = "Do Homework",
        description = "",
        dueDate = 0L,
        priority = 2,
        reletedToSubject = "",
        isCompleted = true,
        taskSubjectId = 2,
        taskId = 2
    ),
    Task(
        title = "GO COACHING",
        description = "",
        dueDate = 0L,
        priority = 3,
        reletedToSubject = "",
        isCompleted = false,
        taskSubjectId = 3,
        taskId = 3
    ),
    // Task(title = "Assignment", description = "", dueDate = 0L, priority = 3, reletedToSubject = "", isCompleted = false, taskSubjectId = 1, taskId = 4),
    //Task(title = "write Poem", description = "", dueDate = 0L, priority = 2, reletedToSubject = "", isCompleted = true, taskSubjectId = 3, taskId = 5),
)

val sessions = listOf(
    Session(
        date = 0L,
        sessionSubjectId = 1,
        relatedToSubject = "English",
        duration = "0.18",
        sessionId = 1,
    ), Session(
        date = 0L,
        sessionSubjectId = 1,
        relatedToSubject = "Maths",
        duration = "0.18",
        sessionId = 1,
    ), Session(
        date = 0L,
        sessionSubjectId = 1,
        relatedToSubject = "Social Science",
        duration = "0.18",
        sessionId = 1,
    )
)
