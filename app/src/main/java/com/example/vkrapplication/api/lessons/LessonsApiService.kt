package com.example.vkrapplication.api.lessons

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface LessonsApiService {
    @GET("courses/{id}/lessons")
    suspend fun getLessons(@Path("id") id : Int): Response<ArrayList<Lesson>>

    @Multipart
    @POST("courses/{id}/lessons")
    suspend fun createLesson(
        @Path("id") id :Int,
        @Part("title") title : String,
        @Part("presentation") presentationFile : MultipartBody.Part,
        @Part("lecture") lectureFile : MultipartBody.Part
    ) : Response<Lesson>
}