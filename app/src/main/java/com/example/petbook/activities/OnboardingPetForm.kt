package com.example.petbook.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.components.FormField
import com.example.petbook.components.FormFieldArea
import com.example.petbook.ui.theme.PetBookTheme
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

class OnboardingPetForm : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetBookTheme(darkTheme = false, dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(top = 28.dp)
                            .padding(top = innerPadding.calculateTopPadding()),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "¡Preséntanos a tus mascotas!",
                                fontSize = 32.sp,
                                textAlign = TextAlign.Center
                            )
                            PetProfileForm()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun PetProfileForm() {
        val agesList = (0..30).toList()
        val emailTextFieldState by remember { mutableStateOf(TextFieldState()) }
        var selectedAge by remember { mutableIntStateOf(agesList[0]) }
        var expanded by remember { mutableStateOf(false) }

        val formFieldModifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
        val textFieldBoxModifier = Modifier.padding(vertical = 8.dp)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Box(modifier = textFieldBoxModifier) {
                FormField(
                    text = "Nombre:",
                    modifier = formFieldModifier,
                    inputType = KeyboardType.Email,
                    textFieldState = emailTextFieldState
                )
            }
            Box(
                modifier = textFieldBoxModifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.clickable { expanded = !expanded },
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("Edad: $selectedAge año(s)")
                    Icon(Icons.Filled.ArrowDropDown, "")
                    DropdownMenu(
                        onDismissRequest = {
                            expanded = false
                        },
                        expanded = expanded,
                        modifier = Modifier.height(256.dp)
                    ) {
                        for (age in agesList) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "$age",
                                        color = if (age == selectedAge)
                                            MaterialTheme.colorScheme.primary
                                        else Color.Black
                                    )
                                },
                                onClick = { selectedAge = age; expanded = false }
                            )
                        }
                    }
                }
            }
            Box(modifier = textFieldBoxModifier) {
                FormField(
                    text = "Raza (opcional):",
                    modifier = formFieldModifier,
                    inputType = KeyboardType.Email,
                    textFieldState = emailTextFieldState
                )
            }
            Column(
                modifier = Modifier.padding(vertical = 32.dp, horizontal = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Añade una foto de tu mascota", modifier = Modifier.padding(bottom = 8.dp))
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
            Button(
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
                    disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor
                ),
                modifier = Modifier.padding(vertical = 8.dp),
                onClick = {
                    val intent = Intent(this@OnboardingPetForm, OnboardingPetForm::class.java)
                    startActivity(intent)
                }) {
                Text(text = "Continuar")
            }
        }
    }
}
