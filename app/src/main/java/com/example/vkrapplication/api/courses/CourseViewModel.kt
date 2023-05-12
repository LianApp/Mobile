package com.example.vkrapplication.api.courses

import androidx.lifecycle.MutableLiveData
import com.example.vkrapplication.api.ApiResponse
import com.example.vkrapplication.api.BaseViewModel
import com.example.vkrapplication.api.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository
) :BaseViewModel() {
    private val _createCourse = MutableLiveData<ApiResponse<Course>>()
    val createCourse = _createCourse

    fun createCourse(
        createCourse: CreateCourse,
        coroutinesErrorHandler: CoroutinesErrorHandler
    ) = baseRequest(
        _createCourse,
        coroutinesErrorHandler
    ){
        courseRepository.createCourse(createCourse)
    }
}