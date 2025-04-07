package com.example.petbook.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.components.FormField
import com.example.petbook.components.FormFieldArea
import com.example.petbook.ui.theme.PetBookTheme


class OnboardingProfileForm : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val formFieldModifier = Modifier
            .fillMaxWidth()
            .height(64.dp)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetBookTheme(darkTheme = false, dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(top = 28.dp)
                            .padding(top = innerPadding.calculateTopPadding()),
                        color = MaterialTheme.colorScheme.background) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Crear perfil", fontSize = 32.sp)
                            UserProfileForm(formFieldModifier)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun UserProfileForm(modifier: Modifier) {
    val emailTextFieldState by remember { mutableStateOf(TextFieldState()) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)) {
            FormField(
                text = "Nombre:",
                modifier = modifier,
                inputType = KeyboardType.Email,
                textFieldState = emailTextFieldState)
        }
        Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)) {
            FormField(
                text = "Nombre de usuario:",
                modifier = modifier,
                inputType = KeyboardType.Email,
                textFieldState = emailTextFieldState)
        }
        Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)) {
            FormField(
                text = "Colonia o localidad (opcional):",
                modifier = modifier,
                inputType = KeyboardType.Email,
                textFieldState = emailTextFieldState)
        }
        Column(
            modifier = Modifier.padding(vertical = 32.dp, horizontal = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Añadir foto de perfil")
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(12.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.add_photo_icon),
                    contentDescription = "add_photo_icon",
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)
                )
            }
        }
        Text("Añade una breve descripción a tu perfil:")
        FormFieldArea(
            modifier = Modifier.fillMaxWidth(),
            inputType = KeyboardType.Text,
            textFieldState = emailTextFieldState,
            maxHeightInLines = 8
        )
    }
}