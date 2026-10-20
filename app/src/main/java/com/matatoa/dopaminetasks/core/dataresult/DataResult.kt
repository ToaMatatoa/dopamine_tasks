package com.matatoa.dopaminetasks.core.dataresult

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

sealed interface DataResult<out T> {
    data class Success<T>(
        val data: T,
    ) : DataResult<T>

    data class Error(
        val error: Throwable,
    ) : DataResult<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<DataResult<T>> =
    map<T, DataResult<T>> { data -> DataResult.Success(data = data) }
        .catch { e -> emit(value = DataResult.Error(error = e)) }
