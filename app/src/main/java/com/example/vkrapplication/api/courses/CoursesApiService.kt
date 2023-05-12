package com.example.vkrapplication.api.courses

import com.example.vkrapplication.api.lessons.Lesson
import retrofit2.Response
import retrofit2.http.*

interface CoursesApiService {
    @POST("courses")
    suspend fun createCourse(
        @Body body : CreateCourse
    ) : Response<Course>
}