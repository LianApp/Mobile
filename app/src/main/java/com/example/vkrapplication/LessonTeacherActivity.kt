package com.example.vkrapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vkrapplication.api.ApiResponse
import com.example.vkrapplication.api.CoroutinesErrorHandler
import com.example.vkrapplication.api.lessons.Lesson
import com.example.vkrapplication.api.lessons.LessonsViewModel
import com.example.vkrapplication.api.main.MainViewModel
import com.example.vkrapplication.api.token.TokenViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.nav_header.*


@AndroidEntryPoint
class LessonTeacherActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private val mainViewModel: MainViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by viewModels()
    private val lessonsViewModel: LessonsViewModel by viewModels()
    private lateinit var recyclerView : RecyclerView
    private var lessonsList: MutableList<Lesson> = ArrayList()
    private lateinit var lessonsAdapter: LessonsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson_teacher)

        val courseId = intent.getIntExtra("courseId",0)

        setContentView(R.layout.activity_lesson_teacher)
        recyclerView = findViewById(R.id.recycler_view_lesson_teacher)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lessonsAdapter = LessonsAdapter(lessonsList)
        recyclerView.adapter = lessonsAdapter

        lessonsViewModel.getLessons(courseId, object : CoroutinesErrorHandler{
            override fun onError(message: String) {
                Toast.makeText(this@LessonTeacherActivity, message, Toast.LENGTH_SHORT).show()
            }
        })

        lessonsViewModel.lessonResponse.observe(this){
            when(it){
                is ApiResponse.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    Log.e("Api", it.errorMessage)
                }
                ApiResponse.Loading -> Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                is ApiResponse.Success -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                    lessonsList.addAll(it.data)
                    lessonsAdapter.notifyDataSetChanged()
                }
                else -> {}
            }
        }

        val addLesson = findViewById<Button>(R.id.add_lesson_button)

        addLesson.setOnClickListener {
            val intent = Intent(this, AddLessonActivity::class.java)
            intent.putExtra("courseId", courseId)
            startActivity(intent)
            finish()
        }

        lessonsAdapter.onItemClick = {

        }

        mainViewModel.getUserInfo(
            object: CoroutinesErrorHandler {
                override fun onError(message: String) {
                    Toast.makeText(this@LessonTeacherActivity, message, Toast.LENGTH_SHORT).show()
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


        val addlessonBtn = findViewById<Button>(R.id.add_lesson_button)

        addlessonBtn.setOnClickListener {
            val intent = Intent(this, AddLessonActivity::class.java)
            intent.putExtra("courseId", courseId)
            startActivity(intent)
            finish()
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