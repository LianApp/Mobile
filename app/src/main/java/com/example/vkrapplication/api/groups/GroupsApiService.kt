package com.example.vkrapplication.api.groups

import retrofit2.Response
import retrofit2.http.GET

interface GroupsApiService {
    @GET("groups")
    suspend fun getGroups() : Response<ArrayList<Groups>>
}