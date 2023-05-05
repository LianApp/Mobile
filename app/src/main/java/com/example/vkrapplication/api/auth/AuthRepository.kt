package com.example.vkrapplication.api.auth

import com.example.vkrapplication.api.Auth
import com.example.vkrapplication.api.apiRequestFlow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
) {
    fun login(auth: Auth) = apiRequestFlow {
        authApiService.login(auth)
    }
}