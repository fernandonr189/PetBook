package com.example.petbook.activities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.petbook.R
import com.example.petbook.components.TextButton
import com.example.petbook.ui.theme.PetBookTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val closeActivity: () -> Unit = {
            this.finish()
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetBookTheme(darkTheme = false, dynamicColor =  false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(all = 16.dp),
                        color = MaterialTheme.colorScheme.background) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.petbook_logo),
                                contentDescription = "petbook_logo",
                                modifier = Modifier
                                    .width(188.dp)
                                    .height(69.dp)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.app_logo),
                                contentDescription = "app_logo",
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp)
                            )
                            TextButton( onClick = { closeActivity() }, text = "Close")
                        }
                    }
                }
            }
        }
    }
}