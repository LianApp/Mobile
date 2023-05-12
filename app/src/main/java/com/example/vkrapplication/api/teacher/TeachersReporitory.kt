package com.example.vkrapplication.api.teacher

import com.example.vkrapplication.api.apiRequestFlow
import javax.inject.Inject

class TeachersRepository @Inject constructor(
    private val teachersApiService: TeachersApiService,
) {
    fun getTeacherCourses() = apiRequestFlow {
        teachersApiService.getTeacherCourses()
    }
}