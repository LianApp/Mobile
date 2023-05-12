package com.example.vkrapplication.api.groups

import androidx.lifecycle.MutableLiveData
import com.example.vkrapplication.api.ApiResponse
import com.example.vkrapplication.api.BaseViewModel
import com.example.vkrapplication.api.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val groupsRepository: GroupsRepository
) : BaseViewModel() {
    private val _groupsResponse = MutableLiveData<ApiResponse<ArrayList<Groups>>>()
    val groupResponse = _groupsResponse

    fun getGroups(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _groupsResponse,
        coroutinesErrorHandler
    ) {
        groupsRepository.getGroups()
    }
}