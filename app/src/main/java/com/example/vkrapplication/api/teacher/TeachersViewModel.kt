package com.example.vkrapplication.api.teacher

import androidx.lifecycle.MutableLiveData
import com.example.vkrapplication.api.ApiResponse
import com.example.vkrapplication.api.BaseViewModel
import com.example.vkrapplication.api.CoroutinesErrorHandler
import com.example.vkrapplication.api.main.UserInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TeachersViewModel @Inject constructor(
    private val teachersRepository: TeachersRepository
) : BaseViewModel(){
    private val _coursesResponse = MutableLiveData<ApiResponse<ArrayList<CourseResponse>>>()
    val coursesResponse = _coursesResponse

    fun getTeachersCourses(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _coursesResponse,
        coroutinesErrorHandler,
    ) {
        teachersRepository.getTeacherCourses()
    }
}