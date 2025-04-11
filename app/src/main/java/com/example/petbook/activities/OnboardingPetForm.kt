package com.example.petbook.activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.text.input.clearText
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import com.example.petbook.components.AlertDialogExample
import com.example.petbook.util.OnboardingStatus
import com.example.petbook.util.bitmapToBase64
import com.example.petbook.util.getCurrentUser
import com.example.petbook.util.storeDocument
import com.example.petbook.util.updateDocument
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class OnboardingPetForm : ComponentActivity() {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var petPictureState: MutableState<Bitmap?> = mutableStateOf(null)
    private var showModal = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        createResultLauncher()
        setContent {
            val petPicture by petPictureState
            val showModal by showModal
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
                            PetProfileForm(petPicture, showModal)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun PetProfileForm(petPicture: Bitmap?, showModal: Boolean) {
        val agesList = (0..30).toList()
        val petNameTextFieldState by remember { mutableStateOf(TextFieldState()) }
        val petRaceTextFieldState by remember { mutableStateOf(TextFieldState()) }
        val descriptionTextFieldState by remember { mutableStateOf(TextFieldState()) }
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
            Column(
                modifier = Modifier.padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Añade una foto de tu mascota", modifier = Modifier.padding(bottom = 8.dp))
                Surface(
                    onClick = {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        resultLauncher.launch(intent)
                    },
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp),
                ) {
                    if (petPicture == null) {
                        Image(
                            painter = painterResource(id = R.drawable.add_photo_icon),
                            contentDescription = "add_photo_icon",
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)
                        )
                    } else {
                        Image(
                            bitmap = petPicture.asImageBitmap(),
                            contentDescription = "pet_picture",
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)
                        )
                    }
                }
            }
            Box(modifier = textFieldBoxModifier) {
                FormField(
                    text = "Nombre:",
                    modifier = formFieldModifier,
                    inputType = KeyboardType.Email,
                    textFieldState = petNameTextFieldState
                )
            }
            Box(
                modifier = textFieldBoxModifier.fillMaxWidth()
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
                        }, expanded = expanded, modifier = Modifier.height(256.dp)
                    ) {
                        for (age in agesList) {
                            DropdownMenuItem(text = {
                                Text(
                                    "$age",
                                    color = if (age == selectedAge) MaterialTheme.colorScheme.primary
                                    else Color.Black
                                )
                            }, onClick = { selectedAge = age; expanded = false })
                        }
                    }
                }
            }
            Box(modifier = textFieldBoxModifier) {
                FormField(
                    text = "Raza (opcional):",
                    modifier = formFieldModifier,
                    inputType = KeyboardType.Email,
                    textFieldState = petRaceTextFieldState
                )
            }
            Text("Añade una breve descripción a tu perfil:")
            FormFieldArea(
                modifier = Modifier.fillMaxWidth(),
                inputType = KeyboardType.Text,
                textFieldState = descriptionTextFieldState,
                maxHeightInLines = 8
            )
            if (showModal) {
                AlertDialogExample(
                    onDismissRequest = {
                        this@OnboardingPetForm.showModal.value = false
                        val intent = Intent(this@OnboardingPetForm, MainScreen::class.java)
                        startActivity(intent)
                        finish()
                    }, onConfirmation = {
                        this@OnboardingPetForm.showModal.value = false

                        // Limpiar los campos del formulario
                        this@OnboardingPetForm.petPictureState.value = null
                        petNameTextFieldState.clearText()
                        petRaceTextFieldState.clearText()
                        descriptionTextFieldState.clearText()
                        selectedAge = 0
                    }, "Continuar", "Desea agregar otra mascota?"
                )
            }
            Button(
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
                    disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor
                ), modifier = Modifier.padding(vertical = 8.dp), onClick = {
                    if (petNameTextFieldState.text.isEmpty()) {
                        Toast.makeText(
                            this@OnboardingPetForm,
                            "Por favor, introduzca un nombre",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    submitPetProfileInfo(
                        petName = petNameTextFieldState.text.toString(),
                        age = selectedAge,
                        race = petRaceTextFieldState.text.toString(),
                        picture = bitmapToBase64(petPicture),
                        description = descriptionTextFieldState.text.toString()
                    )
                }) {
                Text(text = "Continuar")
            }
        }
    }

    private fun submitPetProfileInfo(
        petName: String, age: Int, race: String, picture: String, description: String
    ) {
        val user = getCurrentUser()
        storeDocument(
            Firebase.firestore,
            path = "users/${user!!.uid}/pets",
            document = petName,
            data = hashMapOf(
                "age" to age,
                "race" to race,
                "picture" to picture,
                "description" to description,
            ),
            onSuccess = {
                Toast.makeText(this, "Mascota registrada", Toast.LENGTH_SHORT).show()
            },
            onFail = {
                Toast.makeText(
                    this, "Ocurrio un problema al actualizar su perfil", Toast.LENGTH_SHORT
                ).show()
            })
        updateDocument(
            Firebase.firestore, path = "users", document = user.uid, data = hashMapOf(
                "onboardingStatus" to OnboardingStatus.ONBOARDING_COMPLETE,
            ), onSuccess = {}, onFail = {
                Toast.makeText(
                    this, "Ocurrio un problema al actualizar su perfil", Toast.LENGTH_SHORT
                ).show()
            })
        showModal.value = true
    }

    private fun createResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    petPictureState.value =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            result.data?.getParcelableExtra("data", Bitmap::class.java)
                        } else {
                            @Suppress("DEPRECATION") result.data?.extras?.get("data") as Bitmap
                        }
                }
            }
    }
}
