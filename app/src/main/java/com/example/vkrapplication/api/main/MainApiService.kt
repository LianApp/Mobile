package com.example.vkrapplication.api.main


import retrofit2.Response
import retrofit2.http.GET

interface MainApiService {
    @GET("me")
    suspend fun getUserInfo(): Response<UserInfoResponse>
}