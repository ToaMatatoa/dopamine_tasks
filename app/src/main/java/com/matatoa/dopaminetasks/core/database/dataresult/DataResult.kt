package com.matatoa.dopaminetasks.core.database.dataresult

sealed interface DataResult<out T> {
    data class Success<T>(val data: T) : DataResult<T>
    data class Error(val error: Throwable) : DataResult<Nothing>
}
