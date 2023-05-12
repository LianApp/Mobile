package com.example.vkrapplication.api.teacher

import com.google.gson.annotations.SerializedName

public data class CourseResponse(
    val id: Int,
    val title: String,
    @SerializedName("subject_id")
    val subjectId: Int,
    @SerializedName("teacher_id")
    val teacherId: String,
    val lessons: ArrayList<Lesson>,
    val icon: String
)

public data class Lesson(
    val id: Int,
    @SerializedName("course_id")
    val courseId: Int,
    val title: String,
    @SerializedName("presentation_url")
    val presentationUrl: String,
    @SerializedName("lecture_url")
    val lectureUrl: String
)
