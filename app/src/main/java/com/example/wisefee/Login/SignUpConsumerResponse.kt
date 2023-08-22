package com.example.wisefee.Login

import java.io.Serializable

sealed class SignUpConsumerResponseOrError {
    data class Success(val response: SignUpConsumerResponse) : SignUpConsumerResponseOrError()
    data class Error(val errorResponse: ErrorResponse) : SignUpConsumerResponseOrError()
}
data class SignUpConsumerResponse(
    val id: Int
) : Serializable

data class ErrorResponse(
    val message: String,
    val errors: String
) : Serializable


