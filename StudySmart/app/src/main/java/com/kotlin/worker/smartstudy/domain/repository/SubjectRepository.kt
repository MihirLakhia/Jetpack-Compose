package com.kotlin.worker.smartstudy.domain.repository

import com.kotlin.worker.smartstudy.domain.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {

    suspend fun upsertSubject(subject: Subject)
    fun getTotalSubjectCount(): Flow<Int>
    fun getTotalGoalHours(): Flow<Float>
    suspend fun deleteSubject(subjectId: Int)
    suspend fun getSubjectById(subjectId: Int): Subject?
    fun getAllSubject(): Flow<List<Subject>>
}