package com.matatoa.dopaminetasks.app.screens.habit

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matatoa.dopaminetasks.core.dataresult.DataResult
import com.matatoa.dopaminetasks.domain.model.HabitCompletion
import com.matatoa.dopaminetasks.domain.model.HabitModel
import com.matatoa.dopaminetasks.domain.usecase.habit.AddHabitUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.CheckHabitTodayUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.DeleteHabitUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.FetchHabitCheckedDatesUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.FetchHabitsUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.MarkHabitCompletionShownUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.UncheckHabitTodayUseCase
import com.matatoa.dopaminetasks.domain.usecase.habit.UpdateHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val addHabitUseCase: AddHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val checkHabitTodayUseCase: CheckHabitTodayUseCase,
    private val uncheckHabitTodayUseCase: UncheckHabitTodayUseCase,
    private val markHabitCompletionShownUseCase: MarkHabitCompletionShownUseCase,
    private val fetchHabitCheckedDatesUseCase: FetchHabitCheckedDatesUseCase,
    fetchHabitsUseCase: FetchHabitsUseCase,
) : ViewModel() {

    private val today = MutableStateFlow(value = LocalDate.now())
    private val selectedTabNumber = MutableStateFlow(value = FIRST_TAB_NUMBER)
    private val habits = fetchHabitsUseCase.execute()

    private val _events = Channel<HabitEvent>(capacity = Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    // Everything is resolved inside flatMapLatest so a tab's checks can never be paired
    // with another tab's habit while the selection is changing.
    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<HabitScreenState> = combine(
        habits,
        selectedTabNumber,
        today,
    ) { habitsResult, tabNumber, today ->
        HabitSelection(habitsResult = habitsResult, tabNumber = tabNumber, today = today)
    }.flatMapLatest { selection ->
        selectionState(selection = selection)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = HabitScreenState(today = today.value, isLoading = true),
    )

    init {
        viewModelScope.launch {
            combine(habits, today) { habitsResult, today -> habitsResult to today }
                .collect { (habitsResult, today) ->
                    if (habitsResult is DataResult.Success) {
                        habitsResult.data
                            .filter { it.isMissedCompletionPending(today = today) }
                            .forEach { habit -> showMissedCompletion(habit = habit) }
                    }
                }
        }
    }

    fun selectTab(tabNumber: Int) {
        selectedTabNumber.value = tabNumber
    }

    // The ViewModel outlives midnight while the app is in the background.
    fun refreshToday() {
        today.value = LocalDate.now()
    }

    fun addHabit(tabTitle: String, title: String, daysToComplete: Int) {
        refreshToday()
        val habit = HabitModel(
            id = 0,
            tabNumber = selectedTabNumber.value,
            tabTitle = tabTitle,
            title = title,
            startDate = today.value,
            daysToComplete = daysToComplete,
        )
        viewModelScope.launch {
            sendErrorIfFailed(result = addHabitUseCase.execute(habit = habit))
        }
    }

    fun updateHabit(tabTitle: String, title: String) {
        val habit = state.value.selectedHabit ?: return
        viewModelScope.launch {
            sendErrorIfFailed(
                result = updateHabitUseCase.execute(
                    habit = habit.copy(tabTitle = tabTitle, title = title),
                ),
            )
        }
    }

    fun deleteHabit() {
        val habit = state.value.selectedHabit ?: return
        viewModelScope.launch {
            sendErrorIfFailed(result = deleteHabitUseCase.execute(id = habit.id))
        }
    }

    fun toggleToday() {
        refreshToday()
        val today = today.value
        val habit = state.value.selectedHabit ?: return
        if (!habit.isCheckable(today = today)) return

        val checkedDates = state.value.days.filter { it.isChecked }.map { it.date }
        viewModelScope.launch {
            if (today in checkedDates) {
                sendErrorIfFailed(result = uncheckHabitTodayUseCase.execute(habitId = habit.id))
            } else {
                checkToday(habit = habit, today = today, checkedDates = checkedDates)
            }
        }
    }

    private suspend fun checkToday(
        habit: HabitModel,
        today: LocalDate,
        checkedDates: List<LocalDate>,
    ) {
        when (checkHabitTodayUseCase.execute(habitId = habit.id)) {
            is DataResult.Success -> {
                val completion = habit.completionAfterCheck(
                    today = today,
                    checkedDates = checkedDates,
                )
                if (completion != HabitCompletion.DAY_CHECKED) {
                    markHabitCompletionShownUseCase.execute(id = habit.id)
                }
                _events.send(element = HabitEvent.Completed(completion = completion))
            }

            is DataResult.Error -> _events.send(element = HabitEvent.Error)
        }
    }

    private suspend fun showMissedCompletion(habit: HabitModel) {
        when (markHabitCompletionShownUseCase.execute(id = habit.id)) {
            is DataResult.Success -> _events.send(
                element = HabitEvent.Completed(completion = HabitCompletion.SOME_DAYS_MISSED),
            )

            is DataResult.Error -> Unit
        }
    }

    private suspend fun sendErrorIfFailed(result: DataResult<Unit>) {
        if (result is DataResult.Error) {
            _events.send(element = HabitEvent.Error)
        }
    }

    private fun selectionState(selection: HabitSelection): Flow<HabitScreenState> {
        val errorState = HabitScreenState(
            today = selection.today,
            selectedTabNumber = selection.tabNumber,
            isError = true,
        )
        return when (val result = selection.habitsResult) {
            is DataResult.Error -> flowOf(value = errorState)

            is DataResult.Success -> {
                val habit = result.data.find { it.tabNumber == selection.tabNumber }
                val loadedState = errorState.copy(isError = false, habits = result.data)
                if (habit == null) {
                    flowOf(value = loadedState)
                } else {
                    fetchHabitCheckedDatesUseCase.execute(habitId = habit.id).map { checked ->
                        when (checked) {
                            is DataResult.Success -> loadedState.copy(
                                days = habit.toDays(
                                    today = selection.today,
                                    checkedDates = checked.data,
                                ),
                            )

                            is DataResult.Error -> errorState
                        }
                    }
                }
            }
        }
    }

    private fun HabitModel.toDays(today: LocalDate, checkedDates: List<LocalDate>): List<HabitDay> =
        dates.map { date ->
            HabitDay(
                date = date,
                isChecked = date in checkedDates,
                isCheckable = date == today,
            )
        }

    private data class HabitSelection(
        val habitsResult: DataResult<List<HabitModel>>,
        val tabNumber: Int,
        val today: LocalDate,
    )

    private companion object {
        const val FIRST_TAB_NUMBER = 1
    }
}

@Immutable
data class HabitScreenState(
    val today: LocalDate,
    val selectedTabNumber: Int = 1,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val habits: List<HabitModel> = emptyList(),
    val days: List<HabitDay> = emptyList(),
) {
    val selectedHabit: HabitModel? = habits.find { it.tabNumber == selectedTabNumber }
}

@Immutable
data class HabitDay(
    val date: LocalDate,
    val isChecked: Boolean,
    val isCheckable: Boolean,
)

sealed interface HabitEvent {
    data class Completed(
        val completion: HabitCompletion,
    ) : HabitEvent

    data object Error : HabitEvent
}
