package com.example.vkrapplication.api.courses

import com.example.vkrapplication.api.apiRequestFlow
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val coursesApiService: CoursesApiService,
) {
    fun createCourse(createCourse: CreateCourse
    ) = apiRequestFlow {
        coursesApiService.createCourse(createCourse)
    }
}