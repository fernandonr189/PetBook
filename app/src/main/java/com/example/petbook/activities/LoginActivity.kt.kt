package com.example.petbook.activities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.petbook.R
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
                            .padding(vertical = 28.dp),
                        color = MaterialTheme.colorScheme.background) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 64.dp),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(id = R.drawable.petbook_logo),
                                    contentDescription = "petbook_logo",
                                    modifier = Modifier
                                        .width(250.dp)
                                        .height(92.dp)
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.app_logo),
                                    contentDescription = "app_logo",
                                    modifier = Modifier
                                        .width(133.dp)
                                        .height(133.dp)
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Column {
                                    Text(text = "Si ya tienes una cuenta")
                                    ElevatedButton(
                                        colors = ButtonColors(
                                            containerColor = MaterialTheme.colorScheme.background,
                                            contentColor = Color.Black,
                                            disabledContainerColor = MaterialTheme.colorScheme.background,
                                            disabledContentColor = Color.Black
                                        ),
                                        shape = RectangleShape,
                                        onClick = { closeActivity() }) {
                                        Text("INICIA SESIÓN")
                                    }
                                }
                                ElevatedButton(
                                    colors = ButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = Color.White,
                                        disabledContainerColor = MaterialTheme.colorScheme.primary,
                                        disabledContentColor = Color.White
                                    ),
                                    onClick = { closeActivity() },
                                    shape = RoundedCornerShape(
                                        topStart = 16.dp,
                                        bottomStart = 16.dp),
                                ) {
                                    Text("REGISTRARME")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}