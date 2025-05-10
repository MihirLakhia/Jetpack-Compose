package com.kotlin.worker.smartstudy.data.di

import com.kotlin.worker.smartstudy.data.repository.SessionRepositoryImpl
import com.kotlin.worker.smartstudy.data.repository.SubjectRepositoryImpl
import com.kotlin.worker.smartstudy.data.repository.TaskRepositoryImpl
import com.kotlin.worker.smartstudy.domain.repository.SessionRepository
import com.kotlin.worker.smartstudy.domain.repository.SubjectRepository
import com.kotlin.worker.smartstudy.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSubjectRepository(
        impl:SubjectRepositoryImpl
    ):SubjectRepository


    @Singleton
    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ):TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepository(
        impl:SessionRepositoryImpl
    ): SessionRepository
}