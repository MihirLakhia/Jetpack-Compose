package com.kotlin.worker.smartstudy.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Session(
    val sessionSubjectId: Int,
    val relatedToSubject: String,
    val date: Long,
    val duration: String,
    @PrimaryKey(autoGenerate = true)
    val sessionId: Int? = null,

    )
