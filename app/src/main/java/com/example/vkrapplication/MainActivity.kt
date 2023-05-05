package com.example.vkrapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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
              else -> {false}
          }
      }
    }
}
