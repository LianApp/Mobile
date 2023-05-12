package com.example.vkrapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.vkrapplication.api.ApiResponse
import com.example.vkrapplication.api.CoroutinesErrorHandler
import com.example.vkrapplication.api.auth.AuthViewModel
import com.example.vkrapplication.api.courses.CourseViewModel
import com.example.vkrapplication.api.courses.CreateCourse
import com.example.vkrapplication.api.groups.Groups
import com.example.vkrapplication.api.groups.GroupsViewModel
import com.example.vkrapplication.api.main.MainViewModel
import com.example.vkrapplication.api.subject.Subjects
import com.example.vkrapplication.api.subject.SubjectsViewModel
import com.example.vkrapplication.api.token.TokenViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_course.*
import kotlinx.android.synthetic.main.nav_header.*

@AndroidEntryPoint
class AddCourseActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private val groupsViewModel: GroupsViewModel by viewModels()
    private val subjectsViewModel : SubjectsViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by viewModels()
    private val courseViewModel : CourseViewModel by viewModels()
    private var groupsList: MutableList<Groups> = ArrayList()
    private var groupsStringList: MutableList<String> = ArrayList()
    private var subjectsList : MutableList<Subjects> = ArrayList()
    private var subjectStringList : MutableList<String> = ArrayList()
    private var selectedGroups: ArrayList<Groups> = ArrayList()
    private var selectedGroup : BooleanArray = BooleanArray(256)
    private lateinit var selectedSubject: Subjects

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        val adapterGroup = ArrayAdapter(this, android.R.layout.simple_spinner_item, groupsStringList)
        adapterGroup.setDropDownViewResource(android.R.layout.simple_spinner_item)

        val adapterSubject = ArrayAdapter(this, android.R.layout.simple_spinner_item, subjectStringList)
        adapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_item)
        subject_spinner.adapter = adapterSubject
        subject_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedSubject = subjectsList[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        val groupSelector = findViewById<TextView>(R.id.groups_selector)

        groupSelector.setOnClickListener{
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Выберите группу")
            alertDialog.setCancelable(false)

            alertDialog.setMultiChoiceItems(
                groupsStringList.toTypedArray(),
                selectedGroup
            ) { p0, p1, p2 ->
                if (p2) {
                    selectedGroups.add(groupsList[p1])
                } else {
                    selectedGroups.remove(groupsList[p1])
                }
            }

            alertDialog.setNegativeButton("Отмена"){
                    dialogInterface, i -> dialogInterface.dismiss()
            }
            alertDialog.setPositiveButton("Ок"){
                    dialogInterface, i -> dialogInterface.dismiss()
            }
            alertDialog.show()
        }

        subjectsViewModel.getSubjects(
            object  : CoroutinesErrorHandler{
                override fun onError(message: String) {
                    Toast.makeText(this@AddCourseActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        )


        val addCourseBtn = findViewById<Button>(R.id.add_course_btn)

        addCourseBtn.setOnClickListener {
            val body = CreateCourse(
                course_add_tittle.text.toString(),
                selectedSubject.id,
                selectedGroups.map { it.id } as ArrayList<Int>,
                "\uD83D\uDE00"
            )
            Log.d("Course", body.toString())
            courseViewModel.createCourse(body,object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    Toast.makeText(this@AddCourseActivity, message, Toast.LENGTH_SHORT).show()
                }
            })
        }

        subjectsViewModel.subjectsResponse.observe(this){
            when(it){
                is ApiResponse.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    Log.e("Api", it.errorMessage)
                }
                ApiResponse.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                is ApiResponse.Success -> {
                    subjectsList.addAll(it.data)
                    subjectStringList.clear()
                    subjectStringList.addAll(subjectsList.map { s -> s.name })
                    adapterSubject.notifyDataSetChanged()
                }
            }
        }

        groupsViewModel.getGroups(
            object  : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    Toast.makeText(this@AddCourseActivity,message,Toast.LENGTH_SHORT).show()
                }
            }
        )

        groupsViewModel.groupResponse.observe(this){ it ->
            when(it){
                is ApiResponse.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    Log.e("Api", it.errorMessage)
                }
                ApiResponse.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                is ApiResponse.Success -> {
                    groupsList.addAll(it.data)
                    groupsStringList.clear()
                    groupsStringList.addAll(groupsList.map { g -> g.name })
                    adapterGroup.notifyDataSetChanged()
                    Log.d("groups", groupsList.toString())
                }
            }
        }

        mainViewModel.getUserInfo(
            object: CoroutinesErrorHandler {
                override fun onError(message: String) {
                    Toast.makeText(this@AddCourseActivity, message, Toast.LENGTH_SHORT).show()
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
                R.id.nav_logout -> {
                    startActivity(Intent(this, LoginActivity::class.java))
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