package com.example.petbook

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.petbook.activities.LoginActivity
import com.example.petbook.activities.MainScreen
import com.example.petbook.util.getCurrentUser
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = getCurrentUser()
        val intent = if(currentUser != null) {
            Intent(this, LoginActivity::class.java)
        } else {
            Intent(this, MainScreen::class.java)
        }
        startActivity(intent)
        finish()
    }
}