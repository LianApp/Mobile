package com.example.vkrapplication.api.teacher

import retrofit2.Response
import retrofit2.http.GET

interface TeachersApiService {
    @GET("teacher/courses")
    suspend fun getTeacherCourses(): Response<ArrayList<CourseResponse>>
}