package com.kotlin.worker.smartstudy.data.repository

import com.kotlin.worker.smartstudy.data.local.TaskDao
import com.kotlin.worker.smartstudy.domain.model.Task
import com.kotlin.worker.smartstudy.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
):TaskRepository {
    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(task)
    }

    override suspend fun deleteTask(taskId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        TODO("Not yet implemented")
    }

    override fun getUpcomingTasksForSubject(subjectId: Int): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getCompletedTasksForSubject(subjectId: Int): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getAllUpComingTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
            .map { tasks -> tasks.filter { it.isCompleted.not() } }
            .map { tasks ->sortTasks(tasks) }
    }

    private fun sortTasks(tasks:List<Task>) : List<Task>{
        return tasks.sortedWith(compareBy<Task>{it.dueDate}.thenByDescending { it.priority })
    }
}