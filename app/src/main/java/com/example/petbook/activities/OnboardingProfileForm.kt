package com.example.petbook.activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.components.FormField
import com.example.petbook.components.FormFieldArea
import com.example.petbook.ui.theme.PetBookTheme
import com.example.petbook.util.OnboardingStatus
import com.example.petbook.util.bitmapToBase64
import com.example.petbook.util.getCurrentUser
import com.example.petbook.util.storeDocument
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.io.ByteArrayOutputStream


class OnboardingProfileForm : ComponentActivity() {

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var profilePictureState: MutableState<Bitmap?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        createResultLauncher()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val profilePicture by profilePictureState
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
                            Text("Crear perfil", fontSize = 32.sp, textAlign = TextAlign.Center)
                            UserProfileForm(profilePicture = profilePicture)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun UserProfileForm(profilePicture: Bitmap?) {
        val nameTextFieldState by remember { mutableStateOf(TextFieldState()) }
        val nickNameTextFieldState by remember { mutableStateOf(TextFieldState()) }
        val townTextFieldState by remember { mutableStateOf(TextFieldState()) }
        val descriptionTextFieldState by remember { mutableStateOf(TextFieldState()) }

        val textFieldBoxModifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)
        val formFieldModifier = Modifier
            .fillMaxWidth()
            .height(64.dp)

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
                Text("Añadir foto de perfil", modifier = Modifier.padding(bottom = 8.dp))
                Surface(
                    onClick = {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        resultLauncher.launch(intent)
                    },
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp),
                ) {
                    if (profilePicture == null) {
                        Image(
                            painter = painterResource(id = R.drawable.add_photo_icon),
                            contentDescription = "add_photo_icon",
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)
                        )
                    } else {
                        Image(
                            bitmap = profilePicture.asImageBitmap(),
                            contentDescription = "profile_picture_thumbnail",
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
                    textFieldState = nameTextFieldState
                )
            }
            Box(modifier = textFieldBoxModifier) {
                FormField(
                    text = "Nombre de usuario:",
                    modifier = formFieldModifier,
                    inputType = KeyboardType.Email,
                    textFieldState = nickNameTextFieldState
                )
            }
            Box(modifier = textFieldBoxModifier) {
                FormField(
                    text = "Colonia o localidad (opcional):",
                    modifier = formFieldModifier,
                    inputType = KeyboardType.Email,
                    textFieldState = townTextFieldState
                )
            }
            Text("Añade una breve descripción a tu perfil:")
            FormFieldArea(
                modifier = Modifier.fillMaxWidth(),
                inputType = KeyboardType.Text,
                textFieldState = descriptionTextFieldState,
                maxHeightInLines = 8
            )
            Button(
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
                    disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor
                ), modifier = Modifier.padding(vertical = 8.dp), onClick = {
                    if (profilePicture == null) {
                        Toast.makeText(
                            this@OnboardingProfileForm,
                            "Por favor, agregue una foto de perfil",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (nameTextFieldState.text.isEmpty()) {
                        Toast.makeText(
                            this@OnboardingProfileForm,
                            "Por favor, agregue su nombre",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (nickNameTextFieldState.text.isEmpty()) {
                        Toast.makeText(
                            this@OnboardingProfileForm,
                            "Por favor, agregue un nombre de usuario",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    submitProfileInfo(
                        bitmapToBase64(profilePicture),
                        nameTextFieldState.text.toString(),
                        nickNameTextFieldState.text.toString(),
                        townTextFieldState.text.toString(),
                        descriptionTextFieldState.text.toString(),
                    )
                }) {
                Text(text = "Continuar")
            }
        }
    }

    private fun submitProfileInfo(
        profilePicture: String, name: String, nickname: String, town: String, description: String
    ) {
        val user = getCurrentUser()
        storeDocument(
            Firebase.firestore, path = "users", document = user!!.uid, data = hashMapOf(
            "profilePicture" to profilePicture,
            "name" to name,
            "nickname" to nickname,
            "town" to town,
            "description" to description,
            "onboardingStatus" to OnboardingStatus.PROFILE_COMPLETE
        ), onSuccess = {
            val intent = Intent(this, OnboardingPetForm::class.java)
            startActivity(intent)
        }, onFail = {
            Toast.makeText(
                this, "Ocurrio un problema al actualizar su perfil", Toast.LENGTH_SHORT
            ).show()
        })
    }

    private fun createResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    profilePictureState.value =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            result.data?.getParcelableExtra("data", Bitmap::class.java)
                        } else {
                            @Suppress("DEPRECATION") result.data?.extras?.get("data") as Bitmap
                        }
                }
            }
    }
}
