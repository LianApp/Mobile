package com.example.vkrapplication.api.teacher

import com.example.vkrapplication.api.apiRequestFlow
import javax.inject.Inject

class StudentsRepository @Inject constructor(
    private val studentsApiService: TeachersApiService,
) {
    fun getStudentCourses() = apiRequestFlow {
        studentsApiService.getTeacherCourses()
    }
}