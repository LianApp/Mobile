package com.example.vkrapplication.api.subject

import retrofit2.Response
import retrofit2.http.GET

interface SubjectsApiService {
    @GET("subjects")
    suspend fun getSubjects() : Response<ArrayList<Subjects>>
}