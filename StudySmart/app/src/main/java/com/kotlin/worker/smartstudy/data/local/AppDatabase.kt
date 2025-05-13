package com.kotlin.worker.smartstudy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kotlin.worker.smartstudy.domain.model.Session
import com.kotlin.worker.smartstudy.domain.model.Subject
import com.kotlin.worker.smartstudy.domain.model.Task

@Database(entities = [Subject::class, Session::class, Task::class], version = 1,exportSchema = false)
@TypeConverters(ColorListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun sessionDao(): SessionDao
    abstract fun taskDao(): TaskDao
}