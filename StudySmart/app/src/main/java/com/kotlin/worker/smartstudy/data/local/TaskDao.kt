package com.kotlin.worker.smartstudy.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kotlin.worker.smartstudy.domain.model.Task
import kotlinx.coroutines.flow.Flow
@Dao
interface TaskDao {
    @Upsert
    suspend fun upsertTask(task: Task)

    @Query("SELECT * FROM task where taskId =:taskId")
    suspend fun getTaskById(taskId: Int): Task

    @Query("delete FROM Task where taskId =:taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("delete FROM Task where taskSubjectId =:taskSubjectId")
    suspend fun deleteTaskBySubject(taskSubjectId: Int)

    @Query("SELECT * FROM task where taskSubjectId =:subjectId")
    fun getTasksForSubject(subjectId: Int): Flow<List<Task>>

    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<Task>>


}