package com.example.vkrapplication.api.lessons

import com.example.vkrapplication.api.apiRequestFlow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class LessonsRepository @Inject constructor(
    private val lessonsApiService: LessonsApiService,
) {
    fun getLessons(id : Int) = apiRequestFlow {
        lessonsApiService.getLessons(id)
    }

    fun createLesson(courseId : Int,
                     title : RequestBody,
                     presentationFile : MultipartBody.Part,
                     lectureFile : MultipartBody.Part) = apiRequestFlow {
                         lessonsApiService.createLesson(courseId, title, presentationFile,lectureFile)
    }
}