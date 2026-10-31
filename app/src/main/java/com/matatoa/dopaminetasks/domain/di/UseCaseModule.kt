package com.matatoa.dopaminetasks.domain.di

import com.matatoa.dopaminetasks.domain.usecase.habit.AddHabitUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.AddHabitUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.habit.CheckHabitTodayUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.CheckHabitTodayUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.habit.DeleteHabitUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.DeleteHabitUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.habit.FetchHabitCheckedDatesUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.FetchHabitCheckedDatesUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.habit.FetchHabitsUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.FetchHabitsUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.habit.MarkHabitCompletionShownUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.MarkHabitCompletionShownUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.habit.UncheckHabitTodayUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.UncheckHabitTodayUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.habit.UpdateHabitUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.UpdateHabitUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.task.AddTaskUseCase
import com.matatoa.dopaminetasks.domain.usecase.task.AddTaskUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.task.DeleteTaskUseCase
import com.matatoa.dopaminetasks.domain.usecase.task.DeleteTaskUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.task.DeleteTasksBeforeUseCase
import com.matatoa.dopaminetasks.domain.usecase.task.DeleteTasksBeforeUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.task.FetchTasksUseCase
import com.matatoa.dopaminetasks.domain.usecase.task.FetchTasksUseCaseImpl
import com.matatoa.dopaminetasks.domain.usecase.task.UpdateTaskDoneUseCase
import com.matatoa.dopaminetasks.domain.usecase.task.UpdateTaskDoneUseCaseImpl
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

    @Binds
    fun bindAddHabitUseCase(
        addHabitUseCaseImpl: AddHabitUseCaseImpl,
    ): AddHabitUseCase

    @Binds
    fun bindCheckHabitTodayUseCase(
        checkHabitTodayUseCaseImpl: CheckHabitTodayUseCaseImpl,
    ): CheckHabitTodayUseCase

    @Binds
    fun bindDeleteHabitUseCase(
        deleteHabitUseCaseImpl: DeleteHabitUseCaseImpl,
    ): DeleteHabitUseCase

    @Binds
    fun bindFetchHabitCheckedDatesUseCase(
        fetchHabitCheckedDatesUseCaseImpl: FetchHabitCheckedDatesUseCaseImpl,
    ): FetchHabitCheckedDatesUseCase

    @Binds
    fun bindFetchHabitsUseCase(
        fetchHabitsUseCaseImpl: FetchHabitsUseCaseImpl,
    ): FetchHabitsUseCase

    @Binds
    fun bindMarkHabitCompletionShownUseCase(
        markHabitCompletionShownUseCaseImpl: MarkHabitCompletionShownUseCaseImpl,
    ): MarkHabitCompletionShownUseCase

    @Binds
    fun bindUncheckHabitTodayUseCase(
        uncheckHabitTodayUseCaseImpl: UncheckHabitTodayUseCaseImpl,
    ): UncheckHabitTodayUseCase

    @Binds
    fun bindUpdateHabitUseCase(
        updateHabitUseCaseImpl: UpdateHabitUseCaseImpl,
    ): UpdateHabitUseCase
}
