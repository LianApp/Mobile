package com.example.vkrapplication.api.lessons

import androidx.lifecycle.MutableLiveData
import com.example.vkrapplication.api.ApiResponse
import com.example.vkrapplication.api.BaseViewModel
import com.example.vkrapplication.api.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class LessonsViewModel @Inject constructor(
    private val lessonsRepository: LessonsRepository
) : BaseViewModel(){
    private val _lessonsResponse = MutableLiveData<ApiResponse<ArrayList<Lesson>>>()
    private val _createLessonResponse = MutableLiveData<ApiResponse<Lesson>>()
    val lessonResponse = _lessonsResponse
    val createLessonResponse = _createLessonResponse

    fun getLessons(id : Int,coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _lessonsResponse,
        coroutinesErrorHandler,
    ) {
        lessonsRepository.getLessons(id)
    }

    fun createLesson(
        courseId : Int,
        title : String,
        presentationFile : MultipartBody.Part,
        lectureFile : MultipartBody.Part,
        coroutinesErrorHandler: CoroutinesErrorHandler
    ) = baseRequest(
        _createLessonResponse,
        coroutinesErrorHandler
        ){
            lessonsRepository.createLesson(courseId,title,presentationFile,lectureFile)
    }
}