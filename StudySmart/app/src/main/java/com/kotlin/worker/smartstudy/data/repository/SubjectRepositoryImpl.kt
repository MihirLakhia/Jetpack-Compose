package com.kotlin.worker.smartstudy.data.repository

import com.kotlin.worker.smartstudy.data.local.SessionDao
import com.kotlin.worker.smartstudy.data.local.SubjectDao
import com.kotlin.worker.smartstudy.data.local.TaskDao
import com.kotlin.worker.smartstudy.domain.model.Subject
import com.kotlin.worker.smartstudy.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val subjectDao: SubjectDao,
    private val taskDao: TaskDao,
    private val sessionDao: SessionDao,

    ) : SubjectRepository {
    override suspend fun upsertSubject(subject: Subject) {
        subjectDao.upsertSubject(subject)
    }

    override fun getTotalSubjectCount(): Flow<Int> {
        return subjectDao.getTotalSubjectCount()
    }

    override fun getTotalGoalHours(): Flow<Float> {
        return subjectDao.getTotalGoalHours()
    }

    override suspend fun deleteSubject(subjectId: Int) {
    taskDao.deleteTaskBySubject(subjectId)
    sessionDao.deleteSessionBySubject(subjectId)
    subjectDao.deleteSubject(subjectId)


    }

    override suspend fun getSubjectById(subjectId: Int): Subject? {
        return subjectDao.getSubjectById(subjectId)
    }

    override fun getAllSubject(): Flow<List<Subject>> {
       return subjectDao.getAllSubject()
    }
}