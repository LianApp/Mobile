package com.example.vkrapplication.api.student

import retrofit2.Response
import retrofit2.http.GET

interface StudentsApiService {
    @GET("students/courses")
    suspend fun getStudentCourses(): Response<ArrayList<CourseResponse>>
}