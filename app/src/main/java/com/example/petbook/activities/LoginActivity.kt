package com.example.petbook.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.petbook.R
import com.example.petbook.ui.theme.PetBookTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.example.petbook.util.signUpFirebase
import com.example.petbook.components.FormField
import com.example.petbook.util.OnboardingStatus
import com.example.petbook.util.getDocument
import com.example.petbook.util.loginFirebase
import com.example.petbook.util.passwordReset
import com.example.petbook.util.storeDocument
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class LoginActivity : ComponentActivity() {

    private enum class AuthMode {
        SIGN_UP, LOG_IN
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        db = Firebase.firestore

        val formFieldModifier = Modifier
            .fillMaxWidth()
            .height(64.dp)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var authMode by remember { mutableStateOf(AuthMode.LOG_IN) }
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
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 48.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
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
                                horizontalArrangement = Arrangement.End
                            ) {
                                ElevatedButton(
                                    colors = ButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = Color.White,
                                        disabledContainerColor = MaterialTheme.colorScheme.primary,
                                        disabledContentColor = Color.White
                                    ),
                                    onClick = {
                                        authMode =
                                            if (authMode == AuthMode.SIGN_UP) AuthMode.LOG_IN else AuthMode.SIGN_UP
                                    },
                                    shape = RoundedCornerShape(
                                        topStart = 16.dp, bottomStart = 16.dp
                                    ),
                                ) {
                                    Text(
                                        when (authMode) {
                                            AuthMode.SIGN_UP -> "INICIAR SESIÓN"
                                            AuthMode.LOG_IN -> "REGISTRARME"
                                        }
                                    )
                                }
                            }
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 16.dp),
                                shape = RoundedCornerShape(topStart = 64.dp),
                                color = MaterialTheme.colorScheme.primary
                            ) {
                                when (authMode) {
                                    AuthMode.LOG_IN -> LoginForm(formFieldModifier)
                                    AuthMode.SIGN_UP -> SignupForm(formFieldModifier)
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @Composable
    private fun LoginForm(modifier: Modifier) {
        val emailTextFieldState by remember { mutableStateOf(TextFieldState()) }
        val passwordTextFieldState by remember { mutableStateOf(TextFieldState()) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)) {
                FormField(
                    text = "Correo electrónico:",
                    modifier = modifier,
                    inputType = KeyboardType.Email,
                    textFieldState = emailTextFieldState
                )
            }
            Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)) {
                FormField(
                    text = "Introduzca su contraseña:",
                    modifier = modifier,
                    inputType = KeyboardType.Password,
                    textFieldState = passwordTextFieldState,
                    isPassword = true
                )
            }
            Button(
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
                    disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor
                ), modifier = Modifier.padding(vertical = 8.dp), onClick = {
                    if (emailTextFieldState.text.isEmpty()) {
                        Toast.makeText(
                            this@LoginActivity, "El correo no puede estar vacio", Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (passwordTextFieldState.text.isEmpty()) {
                        Toast.makeText(
                            this@LoginActivity,
                            "La contraseña no puede estar vacia",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    doLogin(
                        emailTextFieldState.text.toString(), passwordTextFieldState.text.toString()
                    )
                }) {
                Text(text = "Continuar")
            }
            Column(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "¿Problemas para iniciar sesión?")
                Button(onClick = {
                    if (emailTextFieldState.text.isEmpty()) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Introduzca un correo electrónico para recuperar su contraseña",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    passwordReset(
                        auth, emailTextFieldState.text.toString(),
                        onEmailSent = {
                            Toast.makeText(
                                this@LoginActivity,
                                "Correo enviado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onFail = { exception ->
                            Toast.makeText(
                                this@LoginActivity,
                                "Ha ocurrido un error al enviar el correo de recuperación: $exception",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                    )
                }) {
                    Text(text = "Olvidé mi contraseña")
                }
            }
        }
    }

    @Composable
    private fun SignupForm(modifier: Modifier) {
        val emailTextFieldState by remember { mutableStateOf(TextFieldState()) }
        val passwordTextFieldState by remember { mutableStateOf(TextFieldState()) }
        val confirmPasswordTextFieldState by remember { mutableStateOf(TextFieldState()) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)) {
                FormField(
                    text = "Correo electrónico:",
                    modifier = modifier,
                    inputType = KeyboardType.Email,
                    textFieldState = emailTextFieldState
                )
            }
            Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)) {
                FormField(
                    text = "Elija una contraseña:",
                    modifier = modifier,
                    inputType = KeyboardType.Password,
                    textFieldState = passwordTextFieldState,
                    isPassword = true
                )
            }
            Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)) {
                FormField(
                    text = "Confirme su contraseña:",
                    modifier = modifier,
                    inputType = KeyboardType.Password,
                    textFieldState = confirmPasswordTextFieldState,
                    isPassword = true
                )
            }
            Button(
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
                    disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor
                ), modifier = Modifier.padding(vertical = 8.dp), onClick = {
                    if (emailTextFieldState.text.isEmpty()) {
                        Toast.makeText(
                            this@LoginActivity,
                            "El correo electrónico no puede estar vacío",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (passwordTextFieldState.text.isEmpty()) {
                        Toast.makeText(
                            this@LoginActivity,
                            "La contraseña no puede estar vacía",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (passwordTextFieldState.text != confirmPasswordTextFieldState.text) {
                        Toast.makeText(
                            this@LoginActivity, "Las contraseñas no coinciden", Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    doSignUp(
                        emailTextFieldState.text.toString(),
                        passwordTextFieldState.text.toString(),
                    )
                }) {
                Text(text = "Continuar")
            }
        }
    }

    private fun doLogin(email: String, password: String) {
        loginFirebase(auth, email, password, onFail = { _ ->
            Toast.makeText(
                this, "El usuario no existe o sus credenciales son incorrectas", Toast.LENGTH_SHORT
            ).show()
        }) { user ->
            getDocument(db, "users", user.uid, { _ ->
                storeDocument(
                    db,
                    "users",
                    user.uid,
                    hashMapOf("onboardingStatus" to OnboardingStatus.NOT_STARTED),
                    {
                        val intent = Intent(this, OnboardingProfileForm::class.java)
                        startActivity(intent)
                    },
                    { _ ->
                        Toast.makeText(
                            this, "Ha ocurrido un problema al iniciar sesión", Toast.LENGTH_SHORT
                        ).show()
                    })
            }) { userData ->
                val onboardingStatus =
                    OnboardingStatus.valueOf(userData["onboardingStatus"].toString())
                val intent = when (onboardingStatus) {
                    OnboardingStatus.NOT_STARTED -> Intent(
                        this, OnboardingProfileForm::class.java
                    )

                    OnboardingStatus.PROFILE_COMPLETE -> Intent(
                        this, OnboardingPetForm::class.java
                    )

                    OnboardingStatus.ONBOARDING_COMPLETE -> Intent(
                        this, MainScreen::class.java
                    )

                    OnboardingStatus.ERROR -> TODO()
                }
                startActivity(intent)
                finish()
            }
        }
    }

    private fun doSignUp(email: String, password: String) {
        signUpFirebase(auth, email, password, onFail = { _ ->
            Toast.makeText(
                this, "El usuario no existe o sus credenciales son incorrectas", Toast.LENGTH_SHORT
            ).show()
        }) { user ->
            storeDocument(
                db,
                "users",
                user.uid,
                hashMapOf("onboardingStatus" to OnboardingStatus.NOT_STARTED),
                {
                    val intent = Intent(this, OnboardingProfileForm::class.java)
                    startActivity(intent)
                    finish()
                },
                { _ ->
                    Toast.makeText(
                        this, "Ha ocurrido un problema al iniciar sesión", Toast.LENGTH_SHORT
                    ).show()
                })
        }
    }
}