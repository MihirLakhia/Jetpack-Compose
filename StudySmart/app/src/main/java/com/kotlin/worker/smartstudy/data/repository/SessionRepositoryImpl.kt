package com.kotlin.worker.smartstudy.data.repository

import com.kotlin.worker.smartstudy.data.local.SessionDao
import com.kotlin.worker.smartstudy.domain.model.Session
import com.kotlin.worker.smartstudy.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sessionDao: SessionDao
) : SessionRepository {
    override suspend fun insertSession(session: Session) {
        sessionDao.insertSession(session)
    }

    override suspend fun deleteSession(session: Session) {
        sessionDao.deleteSession(session)
    }

    override fun getAllSession(): Flow<List<Session>> {
       return sessionDao.getAllSession()
    }

    override fun getRecentFiveSession(): Flow<List<Session>> {
        return sessionDao.getAllSession().take(5)
    }

    override fun getRecentTenSessionForSubject(subjectId: Int): Flow<List<Session>> {
       return sessionDao.getRecentSessionForSubject(subjectId).take(10)
    }

    override fun getTotalSessionDuration(): Flow<Long> {
        return sessionDao.getTotalSessionDuration()
    }

    override fun getTotalSessionDurationBySubject(subjectId: Int): Flow<Long> {
        return sessionDao.getTotalSessionDurationBySubject(subjectId)
    }
}