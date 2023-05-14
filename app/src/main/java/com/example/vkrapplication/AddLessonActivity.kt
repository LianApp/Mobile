package com.example.vkrapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.vkrapplication.api.ApiResponse
import com.example.vkrapplication.api.CoroutinesErrorHandler
import com.example.vkrapplication.api.lessons.LessonsViewModel
import com.example.vkrapplication.api.main.MainViewModel
import com.example.vkrapplication.api.token.TokenViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_lesson.*
import kotlinx.android.synthetic.main.nav_header.*
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

@AndroidEntryPoint
class AddLessonActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private val mainViewModel: MainViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by viewModels()
    private val lessonViewModel : LessonsViewModel by viewModels()
    private var pptxInputStream: InputStream? = null
    private var wordInputStream: InputStream? = null
    private val startPptxPicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri == null) {
                return@registerForActivityResult
            }
            val stream = uploadFile(uri, "pptx")
            pptxInputStream = stream
        }
    private val startWordPicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri == null) {
                return@registerForActivityResult
            }
            val stream = uploadFile(uri, "docx")
            wordInputStream = stream
        }


    private fun getFileExt(uri: Uri): String? {
        val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(
            contentResolver.getType(uri)
        )
    }

    private fun uploadFile(data: Uri, fileExt: String): InputStream? {
        val ext = getFileExt(data)
        if (ext != fileExt) {
            Toast.makeText(
                this@AddLessonActivity,
                "Вы должны загрузить файл в формате $fileExt",
                Toast.LENGTH_SHORT).show()
            return null
        }
        return contentResolver.openInputStream(data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lesson)

        val courseId = intent.getIntExtra("courseId",0)

        val addlessonBtn = findViewById<Button>(R.id.addLesson)
        addlessonBtn.setOnClickListener {
            if (pptxInputStream == null || wordInputStream == null)  {
                Toast.makeText(
                    this@AddLessonActivity,
                    "Attach files first",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pptxBytes = pptxInputStream!!.readBytes()
            val wordBytes = wordInputStream!!.readBytes()
            val pptxPart = MultipartBody.Part.createFormData(
                "presentation",
                "file.pptx",
                pptxBytes.toRequestBody()
            )
            val wordPart = MultipartBody.Part.createFormData(
                "lecture",
                "file.docx",
                wordBytes.toRequestBody()
            )
            lessonViewModel.createLesson(
                courseId,
                lessonTittle.text.toString().toRequestBody(),
                pptxPart,
                wordPart,
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
            startPptxPicker.launch("*/*")
        }


        addLessonLectureBtn.setOnClickListener {
            startWordPicker.launch("*/*")
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