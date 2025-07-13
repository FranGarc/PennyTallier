package com.franciscogarciagarzon.domain.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    companion object {
        fun <T> success(data: T): Result<T> = Success(data)
        fun failure(exception: Throwable): Result<Nothing> = Error(exception)
        fun loading(): Result<Nothing> = Loading
    }
}

abstract class ResultFlowUseCase<in Params, out Type> {

    protected abstract fun execute(params: Params): Flow<Type>

    open operator fun invoke(params: Params, dispatcher: CoroutineDispatcher): Flow<Result<Type>> {
        return flow {
            emit(Result.Loading) // Always emit Loading first

            // Collect from the actual execution flow
            execute(params)
                .flowOn(dispatcher) // Apply dispatcher to the upstream flow
                .catch { e -> emit(Result.Error(e)) } // Catch errors and emit as Result.Error
                .collect { data -> emit(Result.Success(data)) } // On success, emit as Result.Success
        }
    }
}

// For UseCases that don't take any parameters
abstract class ResultFlowUseCaseWithoutParams<out Type> : ResultFlowUseCase<Unit, Type>() {
    operator fun invoke(dispatcher: CoroutineDispatcher): Flow<Result<Type>> {
        return super.invoke(Unit, dispatcher)
    }
}

// For UseCases that don't return a result (e.g., just perform an action)
abstract class ResultFlowUseCaseCompletable<in Params> : ResultFlowUseCase<Params, Unit>() {
    override operator fun invoke(params: Params, dispatcher: CoroutineDispatcher): Flow<Result<Unit>> {
        return super.invoke(params, dispatcher)
    }
}
// For UseCases that neither take parameters nor return a result
abstract class ResultFlowUseCaseCompletableWithoutParams : ResultFlowUseCaseCompletable<Unit>() {
    // This is an overload for convenience, just like ResultFlowUseCaseWithoutParams
    operator fun invoke(dispatcher: CoroutineDispatcher): Flow<Result<Unit>> {
        return super.invoke(Unit, dispatcher)
    }
}