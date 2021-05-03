package com.example.mabna.ui

sealed class ResultData<out T>{
    data class Success<out T>(val data:T):ResultData<T>()
    data class Loading(val isLoading: Boolean) : ResultData<Nothing>()
    data class Failed(val message: String = "") : ResultData<Nothing>()
    data class Exception(val exception: kotlin.Exception) : ResultData<Nothing>()
}
