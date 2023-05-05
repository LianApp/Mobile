package com.example.vkrapplication.api.teacher

import com.example.vkrapplication.api.main.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET

interface TeachersApiService {
    @GET("teachers/courses")
    suspend fun getTeacherCourses(): Response<ArrayList<CourseResponse>>
}