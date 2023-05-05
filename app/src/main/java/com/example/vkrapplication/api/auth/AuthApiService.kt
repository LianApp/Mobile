package com.example.vkrapplication.api.auth


import com.example.vkrapplication.api.Auth
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import com.example.vkrapplication.api.LoginResponse
import retrofit2.Response

interface AuthApiService {
    @POST("login")
    suspend fun login(
        @Body auth: Auth,
    ): Response<LoginResponse>

    @GET("refresh")
    suspend fun refreshToken(
        @Header("Authorization") token: String,
    ): Response<LoginResponse>
}