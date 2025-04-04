package com.example.petbook
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.petbook.activities.LoginActivity
import com.example.petbook.ui.theme.PetBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetBookTheme {
            }
        }
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}