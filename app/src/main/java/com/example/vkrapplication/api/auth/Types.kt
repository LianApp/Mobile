package com.example.vkrapplication.api

import com.google.gson.annotations.SerializedName

data class Auth(
    val email: String,
    val password: String
)

public data class LoginResponse(
    @SerializedName("accessToken")
    val token: String,
    @SerializedName("refreshToken")
    val refresh: String
)

data class ErrorResponse(
    val statusCode: Int,
    val error: String,
    val message: String?
)