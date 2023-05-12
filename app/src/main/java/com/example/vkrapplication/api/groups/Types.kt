package com.example.vkrapplication.api.groups

import com.google.gson.annotations.SerializedName

public data class Groups(
    val id: Int,
    val name: String,
    @SerializedName("oranization_id")
    val organizationId: Int
)
