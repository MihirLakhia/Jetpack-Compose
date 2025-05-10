package com.kotlin.worker.smartstudy.domain.repository

import androidx.room.Delete
import androidx.room.Query
import com.kotlin.worker.smartstudy.domain.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun insertSession(session: Session)

    suspend fun deleteSession(session: Session)

    fun getAllSession(): Flow<List<Session>>

    fun getRecentFiveSession(): Flow<List<Session>>
    fun getRecentTenSession(subjectId: Int): Flow<List<Session>>

    fun getTotalSessionDuration(): Flow<Long>

    fun getTotalSessionDurationBySubject(subjectId: Int): Flow<Long>

}