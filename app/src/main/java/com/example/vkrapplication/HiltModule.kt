package com.example.vkrapplication
import com.example.vkrapplication.api.auth.AuthApiService
import com.example.vkrapplication.api.auth.AuthRepository
import com.example.vkrapplication.api.courses.CourseRepository
import com.example.vkrapplication.api.courses.CoursesApiService
import com.example.vkrapplication.api.groups.GroupsApiService
import com.example.vkrapplication.api.groups.GroupsRepository
import com.example.vkrapplication.api.lessons.LessonsApiService
import com.example.vkrapplication.api.lessons.LessonsRepository
import com.example.vkrapplication.api.main.MainApiService
import com.example.vkrapplication.api.main.MainRepository
import com.example.vkrapplication.api.student.StudentsApiService
import com.example.vkrapplication.api.student.StudentsRepository
import com.example.vkrapplication.api.subject.SubjectsApiService
import com.example.vkrapplication.api.subject.SubjectsRepository
import com.example.vkrapplication.api.teacher.TeachersApiService
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

    @Provides
    fun provideLessonsRepository(lessonsApiService: LessonsApiService) = LessonsRepository(lessonsApiService)

    @Provides
    fun provideGroupsRepository(groupsApiService: GroupsApiService) = GroupsRepository(groupsApiService)

    @Provides
    fun provideSubjectsRepository(subjectsApiService: SubjectsApiService) = SubjectsRepository(subjectsApiService)

    @Provides
    fun provideCoursesRepository(coursesApiService: CoursesApiService) = CourseRepository(coursesApiService)
}