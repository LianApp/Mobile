package com.example.vkrapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.vkrapplication.api.ApiResponse
import com.example.vkrapplication.api.CoroutinesErrorHandler
import com.example.vkrapplication.api.courses.CreateCourse
import com.example.vkrapplication.api.lessons.CreateLesson
import com.example.vkrapplication.api.lessons.Lesson
import com.example.vkrapplication.api.lessons.LessonsViewModel
import com.example.vkrapplication.api.main.MainViewModel
import com.example.vkrapplication.api.token.TokenViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_lesson.*
import kotlinx.android.synthetic.main.activity_student_profile.*
import kotlinx.android.synthetic.main.nav_header.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@AndroidEntryPoint
class AddLessonActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private val mainViewModel: MainViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by viewModels()
    private val lessonViewModel : LessonsViewModel by viewModels()

    lateinit var selectedFile1 : String
    lateinit var selectedFile2 : String

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            selectedFile1 = data?.data?.path.toString()
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            selectedFile2 = data?.data?.path.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lesson)



        val courseId = intent.getIntExtra("courseId",0)

        var filePres = File(selectedFile1)
        var fileLecture = File(selectedFile2)
        var requestPresFile : RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), filePres)
        var requestLectFile : RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fileLecture)
        val presMulti : MultipartBody.Part = MultipartBody.Part.createFormData("pres", filePres.name, requestPresFile)
        val lectMulti : MultipartBody.Part = MultipartBody.Part.createFormData("lect",fileLecture.name,requestLectFile)

        val addlessonBtn = findViewById<Button>(R.id.addLesson)
        addlessonBtn.setOnClickListener {
            lessonViewModel.createLesson(
                courseId,
                lessonTittle.text.toString(),
                presMulti,
                lectMulti,
                object  : CoroutinesErrorHandler{
                    override fun onError(message: String) {
                        Toast.makeText(this@AddLessonActivity, message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        mainViewModel.getUserInfo(
            object: CoroutinesErrorHandler {
                override fun onError(message: String) {
                    Toast.makeText(this@AddLessonActivity, message, Toast.LENGTH_SHORT).show()
                }
            })

        mainViewModel.userInfoResponse.observe(this){
            when(it){
                is ApiResponse.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    Log.e("Api",it.errorMessage)
                }
                ApiResponse.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                is ApiResponse.Success -> {
                    nav_name.text = it.data.name
                }
                else -> {}
            }
        }

        val addLessonPresentationBtn = findViewById<Button>(R.id.addPresentation)
        val addLessonLectureBtn = findViewById<Button>(R.id.addLecture)

        addLessonPresentationBtn.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Выберете файл презентации"), 1)
        }


        addLessonLectureBtn.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Выберете файл лекции"), 2)
        }



        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profile_teacher -> {
                    startActivity(Intent(this, TeacherProfile::class.java))
                    true
                }
                R.id.results -> {
                    startActivity(Intent(this, ResultActivity::class.java))
                    true
                }
                R.id.courses -> {
                    startActivity(Intent(this, CourseTeacherActivity::class.java))
                    true
                }
                R.id.nav_logout ->{
                    tokenViewModel.deleteToken()
                    startActivity(Intent(this, LoginActivity::class.java))
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}