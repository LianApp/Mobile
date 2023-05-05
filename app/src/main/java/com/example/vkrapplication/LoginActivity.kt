package com.example.vkrapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.vkrapplication.api.*
import com.example.vkrapplication.api.auth.AuthViewModel
import com.example.vkrapplication.api.main.MainViewModel
import com.example.vkrapplication.api.token.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_student_profile.*
import kotlinx.android.synthetic.main.nav_header.*


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        val loginBtn = findViewById<Button>(R.id.loginButton)
        val orgBtn = findViewById<Button>(R.id.AddOrg)

        loginBtn.setOnClickListener {
            var login = userName.text.toString()
            var pass = userPassword.text.toString()
            if(userName.text == null && userPassword == null){
                Toast.makeText(this@LoginActivity, "Поля пусты", Toast.LENGTH_SHORT).show()
            } else if(login == null || pass == null){
                Toast.makeText(this@LoginActivity, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(
                    Auth(login, pass),
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                mainViewModel.userInfoResponse.observe(this){
                    when(it){
                        is ApiResponse.Failure -> {
                            Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                            Log.e("Api",it.errorMessage)
                        }
                        ApiResponse.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                        is ApiResponse.Success -> {
                            when(it.data.role){
                                "TEACHER" -> {
                                    val intent = Intent(this, TeacherProfile::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                "STUDENT" -> {
                                    val intent = Intent(this, StudentProfile::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                else -> {

                                }
                            }
                        }
                        else -> {}
                    }
                }
            }


        }

        tokenViewModel.token.observe(this) { token ->
            if (token != null){
                mainViewModel.getUserInfo(
                    object: CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }

        viewModel.loginResponse.observe(this) {
            when(it) {
                is ApiResponse.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    Log.e("Api",it.errorMessage)
                }
                ApiResponse.Loading -> Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                is ApiResponse.Success -> {
                    tokenViewModel.saveToken(it.data.token)
                }
            }
        }

        orgBtn.setOnClickListener {
            val intent = Intent(this, AddOrgActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}