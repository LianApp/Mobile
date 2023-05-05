package com.example.vkrapplication
import com.example.vkrapplication.api.auth.AuthApiService
import com.example.vkrapplication.api.auth.AuthRepository
import com.example.vkrapplication.api.main.MainApiService
import com.example.vkrapplication.api.main.MainRepository
import com.example.vkrapplication.api.student.StudentsApiService
import com.example.vkrapplication.api.student.StudentsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun provideAuthRepository(authApiService: AuthApiService) = AuthRepository(authApiService)

    @Provides
    fun provideMainRepository(mainApiService: MainApiService) = MainRepository(mainApiService)

    @Provides
    fun provideStudentRepository(studentApiService: StudentsApiService) = StudentsRepository(studentApiService)
}