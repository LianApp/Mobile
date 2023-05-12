package com.example.vkrapplication.api.subject

import com.example.vkrapplication.api.apiRequestFlow
import javax.inject.Inject

class SubjectsRepository @Inject constructor(
    private val subjectsApiService: SubjectsApiService
) {
    fun getSubjects() = apiRequestFlow {
        subjectsApiService.getSubjects()
    }
}