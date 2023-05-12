package com.example.vkrapplication.api.subject

import androidx.lifecycle.MutableLiveData
import com.example.vkrapplication.api.ApiResponse
import com.example.vkrapplication.api.BaseViewModel
import com.example.vkrapplication.api.CoroutinesErrorHandler
import com.example.vkrapplication.api.groups.Groups
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubjectsViewModel @Inject constructor(
    private val subjectsRepository: SubjectsRepository
) : BaseViewModel(){
    private val _subjectResponse = MutableLiveData<ApiResponse<ArrayList<Subjects>>>()
    val subjectsResponse = _subjectResponse

    fun getSubjects(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _subjectResponse,
        coroutinesErrorHandler
    ){
        subjectsRepository.getSubjects()
    }
}