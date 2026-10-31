package com.matatoa.dopaminetasks.domain.di

import com.matatoa.dopaminetasks.domain.usecase.AddTaskUseCase
import com.matatoa.dopaminetasks.domain.usecase.AddTaskUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.DeleteTaskUseCase
import com.matatoa.dopaminetasks.domain.usecase.DeleteTaskUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.DeleteTasksBeforeUseCase
import com.matatoa.dopaminetasks.domain.usecase.DeleteTasksBeforeUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.FetchTasksUseCase
import com.matatoa.dopaminetasks.domain.usecase.FetchTasksUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.UpdateTaskDoneUseCase
import com.matatoa.dopaminetasks.domain.usecase.UpdateTaskDoneUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindAddTaskUseCase(addTaskUseCaseImpl: AddTaskUseCaseImpl): AddTaskUseCase

    @Binds
    fun bindDeleteTaskUseCase(deleteTaskUseCaseImpl: DeleteTaskUseCaseImpl): DeleteTaskUseCase

    @Binds
    fun bindDeleteTasksBeforeUseCase(
        deleteTasksBeforeUseCaseImpl: DeleteTasksBeforeUseCaseImpl,
    ): DeleteTasksBeforeUseCase

    @Binds
    fun bindFetchTasksUseCase(fetchTasksUseCaseImpl: FetchTasksUseCaseImpl): FetchTasksUseCase

    @Binds
    fun bindUpdateTaskDoneUseCase(
        updateTaskDoneUseCaseImpl: UpdateTaskDoneUseCaseImpl,
    ): UpdateTaskDoneUseCase
}
