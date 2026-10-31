package com.matatoa.dopaminetasks.core.di

import com.matatoa.dopaminetasks.core.repository.HabitRepository
import com.matatoa.dopaminetasks.core.repository.HabitRepositoryImpl
import com.matatoa.dopaminetasks.core.repository.TaskRepository
import com.matatoa.dopaminetasks.core.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindTaskRepository(taskRepositoryImpl: TaskRepositoryImpl): TaskRepository

    @Binds
    fun bindHabitRepository(habitRepositoryImpl: HabitRepositoryImpl): HabitRepository
}
