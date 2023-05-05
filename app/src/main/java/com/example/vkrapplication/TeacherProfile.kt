package com.example.vkrapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class TeacherProfile : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_profile)


        val resBtn = findViewById<Button>(R.id.ResultBtn)
        val addLessBtn = findViewById<Button>(R.id.AddLessonBtn)

        addLessBtn.setOnClickListener {
            val intent = Intent(this, AddLessonActivity::class.java)
            startActivity(intent)
            finish()
        }

        resBtn.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
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
                R.id.add_lesson -> {
                    startActivity(Intent(this, AddLessonActivity::class.java))
                    true
                }
                R.id.results -> {
                    startActivity(Intent(this, ResultActivity::class.java))
                    true
                }
                R.id.nav_logout ->{
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