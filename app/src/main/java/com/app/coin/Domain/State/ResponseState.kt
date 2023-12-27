package com.app.coin.Domain.State

sealed class ResponseState<out T>
{
    data class Success<T>(val data:T):ResponseState<T>()
    data class Error(val message:String):ResponseState<Nothing>()
    object Loading:ResponseState<Nothing>()
    fun toData():T? = if (this is Success) data else null
}
