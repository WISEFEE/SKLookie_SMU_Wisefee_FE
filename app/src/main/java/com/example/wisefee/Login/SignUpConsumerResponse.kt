package com.example.wisefee.Login

import java.io.Serializable

//abstract class SignUpConsumerResponseOrError {
//    data class Success(val response: SignUpConsumerResponse) : SignUpConsumerResponseOrError()
//    data class Error(val errorResponse: ErrorResponse) : SignUpConsumerResponseOrError()
//}
data class SignUpConsumerResponse(
    val id: String
) : Serializable

//data class ErrorResponse(
//    val message: String,
//    val errors: String
//) : Serializable


