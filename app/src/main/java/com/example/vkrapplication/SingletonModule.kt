package com.example.vkrapplication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.vkrapplication.api.auth.AuthApiService
import com.example.vkrapplication.api.auth.AuthAuthenticator
import com.example.vkrapplication.api.auth.AuthInterceptor
import com.example.vkrapplication.api.courses.CoursesApiService
import com.example.vkrapplication.api.groups.GroupsApiService
import com.example.vkrapplication.api.lessons.LessonsApiService
import com.example.vkrapplication.api.main.MainApiService
import com.example.vkrapplication.api.student.StudentsApiService
import com.example.vkrapplication.api.subject.SubjectsApiService
import com.example.vkrapplication.api.teacher.TeachersApiService
import com.example.vkrapplication.api.token.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager = TokenManager(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)

    @Singleton
    @Provides
    fun provideAuthAuthenticator(tokenManager: TokenManager): AuthAuthenticator =
        AuthAuthenticator(tokenManager)

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl("http://192.168.1.7:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideAuthAPIService(retrofit: Retrofit.Builder): AuthApiService =
        retrofit
            .build()
            .create(AuthApiService::class.java)

    @Singleton
    @Provides
    fun provideMainAPIService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): MainApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(MainApiService::class.java)

    @Singleton
    @Provides
    fun provideStudentApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): StudentsApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(StudentsApiService::class.java)

    @Singleton
    @Provides
    fun provideTeacherApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): TeachersApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(TeachersApiService::class.java)

    @Singleton
    @Provides
    fun provideLessonsApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): LessonsApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(LessonsApiService::class.java)

    @Singleton
    @Provides
    fun provideGroupsApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder) : GroupsApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(GroupsApiService::class.java)

    @Singleton
    @Provides
    fun provideSubjectsApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder) : SubjectsApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(SubjectsApiService::class.java)

    @Singleton
    @Provides
    fun provideCourseApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder) : CoursesApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(CoursesApiService::class.java)
}