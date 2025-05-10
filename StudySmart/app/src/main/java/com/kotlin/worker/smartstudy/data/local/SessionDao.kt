package com.kotlin.worker.smartstudy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kotlin.worker.smartstudy.domain.model.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Insert
    suspend fun insertSession(session: Session)

    @Delete
    suspend fun deleteSession(session: Session)

    @Query("SELECT * FROM Session")
    fun getAllSession(): Flow<List<Session>>

    @Query("SELECT * FROM session where sessionSubjectId =:subjectId")
    fun getRecentSessionForSubject(subjectId: Int): Flow<List<Session>>

    @Query("SELECT sum(duration) FROM session")
    fun getTotalSessionDuration(): Flow<Long>

    @Query("SELECT sum(duration) FROM session where sessionSubjectId = :subjectId")
    fun getTotalSessionDurationBySubject(subjectId: Int): Flow<Long>

    @Query("Delete FROM session where sessionSubjectId = :subjectId")
    fun deleteSessionBySubject(subjectId: Int)

}