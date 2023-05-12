package com.example.vkrapplication.api.groups

import com.example.vkrapplication.api.apiRequestFlow
import javax.inject.Inject

class GroupsRepository @Inject constructor(
    private val groupApiSevice : GroupsApiService,
) {
    fun getGroups() = apiRequestFlow {
        groupApiSevice.getGroups()
    }
}