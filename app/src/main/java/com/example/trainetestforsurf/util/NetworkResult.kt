package com.example.trainetestforsurf.util


sealed class NetworkResult<out T,out E>{
    data class Success<T>(val data : T) : NetworkResult<T,Nothing>()
    data class Error<E>(val message : String) : NetworkResult<Nothing,E>()
}

inline fun <S,E,R> NetworkResult<S,E>.mapResponse(block : (S) -> R) : NetworkResult<R,E>{
    return when(this){
        is NetworkResult.Success -> NetworkResult.Success(data = block(this.data))
        is NetworkResult.Error -> NetworkResult.Error(message = "error")
    }
}
inline fun <S,E> NetworkResult<S,E>.doOnSuccess(block: (S) -> Unit) : NetworkResult<S,E>{
    if (this is NetworkResult.Success){
        block(this.data)
    }
    return this
}
