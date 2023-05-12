package com.example.vkrapplication.api.courses


public data class Course(
    val id: Int,
    val title: String,
    val subjectId: Int,
    val groupIds: ArrayList<Groups>,
    val icon: String
)

public data class Groups(
    val id: Int,
    val title: String
)

public data class CreateCourse(
    val title : String,
    val subjectId : Int,
    val groupIds : ArrayList<Int>,
    val icon: String
)