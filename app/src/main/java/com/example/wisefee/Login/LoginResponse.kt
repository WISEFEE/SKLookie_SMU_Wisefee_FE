package com.example.wisefee.Login

import java.io.Serializable

class LoginResponse(
    var grantType : String? = null,
    var accessToken : String? = null
): Serializable
