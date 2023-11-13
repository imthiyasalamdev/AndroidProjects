package com.example.chatapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.chatapplication.R
import com.example.chatapplication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var appLogo: ImageView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        nameEditText = findViewById(R.id.name)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        signUpButton = findViewById(R.id.signUpButton)
        appLogo = findViewById(R.id.appLogo)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        signUpButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            signUp(name, email, password)
        }
    }

    private fun signUp(name: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user != null) {

                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                        user.updateProfile(profileUpdates)
                    }
                    storeUserDataInDatabase(name, email)
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                    Toast.makeText(this@SignUp, "SignUp successful", Toast.LENGTH_LONG).show()
                } else {
                    val exception = task.exception
                    if (exception is FirebaseAuthException) {
                        val errorCode = exception.errorCode
                        val errorMessage = exception.message
                        Toast.makeText(this@SignUp, "Error: $errorCode - $errorMessage", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@SignUp, "Some error occurred", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }


    private fun storeUserDataInDatabase(name: String, email: String) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val uid = currentUser.uid


            val database = FirebaseDatabase.getInstance()
            val userRef: DatabaseReference = database.getReference("users")


            val userData = User(name, email, uid)


            userRef.child(uid).setValue(userData)
        }
    }
}
