package com.example.vkrapplication.api.subject

import com.google.gson.annotations.SerializedName

public data class Subjects(
    val id: Int,
    val name: String,
    @SerializedName("oranization_id")
    val organizationId: Int
)
