package com.matatoa.dopaminetasks.core.di

import com.matatoa.dopaminetasks.core.database.repository.TaskRepository
import com.matatoa.dopaminetasks.core.database.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTaskRepository(taskRepositoryImpl: TaskRepositoryImpl):
        TaskRepository
}
