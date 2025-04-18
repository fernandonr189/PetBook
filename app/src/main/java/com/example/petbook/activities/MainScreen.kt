package com.example.petbook.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.petbook.ui.theme.PetBookTheme

class MainScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetBookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Text(modifier = Modifier.padding(innerPadding), text = "Placeholder para feed")
                }
            }
        }
    }
}
