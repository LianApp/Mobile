package com.example.vkrapplication.api.student

import androidx.lifecycle.MutableLiveData
import com.example.vkrapplication.api.ApiResponse
import com.example.vkrapplication.api.BaseViewModel
import com.example.vkrapplication.api.CoroutinesErrorHandler
import com.example.vkrapplication.api.main.UserInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val studentsRepository: StudentsRepository
) : BaseViewModel(){
    private val _coursesResponse = MutableLiveData<ApiResponse<ArrayList<CourseResponse>>>()
    val coursesResponse = _coursesResponse

    fun getStudentCourses(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _coursesResponse,
        coroutinesErrorHandler,
    ) {
        studentsRepository.getStudentCourses()
    }
}