package com.example.vkrapplication.api.lessons

import com.example.vkrapplication.api.apiRequestFlow
import okhttp3.MultipartBody
import javax.inject.Inject

class LessonsRepository @Inject constructor(
    private val lessonsApiService: LessonsApiService,
) {
    fun getLessons(id : Int) = apiRequestFlow {
        lessonsApiService.getLessons(id)
    }

    fun createLesson(courseId : Int,
                     title : String,
                     presentationFile : MultipartBody.Part,
                     lectureFile : MultipartBody.Part) = apiRequestFlow {
                         lessonsApiService.createLesson(courseId, title, presentationFile,lectureFile)
    }
}