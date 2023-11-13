package com.example.chatapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.chatapplication.R
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var appLogo: ImageView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)
        signUpButton = findViewById(R.id.signUpButton)
        appLogo = findViewById(R.id.appLogo)
        firebaseAuth = FirebaseAuth.getInstance()
        signUpButton.setOnClickListener {
            val intent = Intent(this@Login, SignUp::class.java)
            finish()
            startActivity(intent)
        }
        loginButton.setOnClickListener {

            val email = email.text.toString()
            val password = password.text.toString()
            login(email, password);
        }
    }

    private fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@Login, "Login successful", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@Login, "user doesn't exist", Toast.LENGTH_LONG).show()
                }
            }
    }

}
