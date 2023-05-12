package com.example.vkrapplication.api.student

import com.example.vkrapplication.api.apiRequestFlow
import javax.inject.Inject

class StudentsRepository @Inject constructor(
    private val studentsApiService: StudentsApiService,
) {
    fun getStudentCourses() = apiRequestFlow {
        studentsApiService.getStudentCourses()
    }
}