package com.example.wisefee.Login

import java.io.Serializable

data class LoginResponse(
    var grantType : String? = null,
    var accessToken : String? = null
): Serializable

data class MemberResponse(
    val accountType: String?,
    val authType: String?,
    val birth: String?,
    val createdAt: String?,
    val email: String?,
    val isAllowPushMsg: String?,
    val isAuthEmail: String?,
    val memberId: String?,
    val memberStatus: String?,
    val nickname: String?,
    val phone: String?,
    val phoneOffice: String?,
    val pushMsgToken: String?,
    val updatedAt: String?
) : Serializable


data class SignUpConsumerRequest(
    val email: String,
    val password: String,
    val nickname: String,
    val accountType: String,
    val authType: String,
    val birth: String,
    val isAllowPushMsg: String,
    val isAuthEmail: String,
    val phone: String,
    val phoneOffice: String
)

data class LoginRequest(
    val email: String,
    val fcmToken: String,
    val password: String
)

data class UpdateMemberRequest(
    val nickname: String,
    val birth: String,
    val isAllowPushMsg: String,
    val isAuthEmail: String,
    val phone: String,
    val phoneOffice: String
)

