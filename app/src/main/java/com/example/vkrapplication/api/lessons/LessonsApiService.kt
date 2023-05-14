package com.example.vkrapplication.api.lessons

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface LessonsApiService {
    @GET("courses/{id}/lessons")
    suspend fun getLessons(@Path("id") id : Int): Response<ArrayList<Lesson>>

    @Multipart
    @POST("course/{id}/lessons")
    suspend fun createLesson(
        @Path("id") id : Int,
        @Part("title") title : RequestBody,
        @Part presentationFile : MultipartBody.Part,
        @Part lectureFile : MultipartBody.Part
    ) : Response<Lesson>
}