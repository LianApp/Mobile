package com.example.vkrapplication.api.main

public data class UserInfoResponse(
    val id: Int,
    val name: String,
    val role: String,
    val email: String,
    val group: StudentGroup
)

public data class StudentGroup(
    val id: Int,
    val name: String
)
