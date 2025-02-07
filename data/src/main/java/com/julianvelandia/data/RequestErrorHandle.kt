package com.julianvelandia.data


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.UnknownHostException


inline fun <T, R> T.safeCallData(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline localCall: suspend T.() -> R?,
    crossinline remoteCall: suspend T.() -> Result<R>,
    crossinline updateLocalData: suspend T.(R) -> Unit,
): Flow<Result<R>> = flow {
    val localResult: R? = runCatching {
        localCall()
    }.getOrElse { null }

    if (localResult != null && (localResult !is List<*> || localResult.isNotEmpty())) {
        emit(Result.success(localResult))
    }

    try {
        val remoteResult = remoteCall()
        remoteResult.fold(
            onSuccess = { data ->
                updateLocalData(data)
                emit(Result.success(data))
            },
            onFailure = { e ->
                if (localResult == null) {
                    emit(Result.failure(e))
                }
            }
        )
    } catch (e: Exception) {
        if (localResult == null || (localResult is List<*> && localResult.isEmpty())) {
            emit(Result.failure(e))
        }
    }
}.flowOn(dispatcher)

