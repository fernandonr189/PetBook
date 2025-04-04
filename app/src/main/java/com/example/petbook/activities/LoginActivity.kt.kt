package com.example.petbook.activities
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petbook.R
import com.example.petbook.ui.theme.PetBookTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.example.petbook.util.createUserAccount

class LoginActivity : ComponentActivity(){

    private lateinit var auth: FirebaseAuth

    private fun signupButtonOnClick(email: String, password: String)  {
        createUserAccount(
            auth,
            email,
            password,
            this,
            {
                Toast.makeText(this, "Cuenta creada", Toast.LENGTH_SHORT).show()
            },
            {
                Toast.makeText(this, "Error al crear la cuenta", Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth

        val formFieldModifier = Modifier
            .fillMaxWidth()
            .height(64.dp)

        val emailTextFieldState = TextFieldState()
        val passwordTextFieldState = TextFieldState()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetBookTheme(darkTheme = false, dynamicColor =  false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(top = 28.dp)
                            .padding(top = innerPadding.calculateTopPadding()),
                        color = MaterialTheme.colorScheme.background) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 48.dp),
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
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(horizontal = 16.dp)) {
                                    Text(text = "Si ya tienes una cuenta", fontSize = 12.sp)
                                    ElevatedButton(
                                        colors = ButtonColors(
                                            containerColor = MaterialTheme.colorScheme.background,
                                            contentColor = Color.Black,
                                            disabledContainerColor = MaterialTheme.colorScheme.background,
                                            disabledContentColor = Color.Black
                                        ),
                                        shape = RectangleShape,
                                        onClick = { Toast.makeText(applicationContext, "Unimplemented", Toast.LENGTH_SHORT).show() }) {
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
                                    onClick = {
                                        signupButtonOnClick(emailTextFieldState.text.toString(),
                                        passwordTextFieldState.text.toString()) },
                                    shape = RoundedCornerShape(
                                        topStart = 16.dp,
                                        bottomStart = 16.dp),
                                ) {
                                    Text("REGISTRARME")
                                }
                            }
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 16.dp),
                                shape = RoundedCornerShape(topStart = 64.dp),
                                color = MaterialTheme.colorScheme.primary) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp)
                                ) {
                                    Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)) {
                                        FormField(
                                            text = "Correo electrónico:",
                                            modifier = formFieldModifier,
                                            inputType = KeyboardType.Email,
                                            textFieldState = emailTextFieldState)
                                    }
                                    Box(modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp)) {
                                        FormField(
                                            text = "Elija una contraseña:",
                                            modifier = formFieldModifier,
                                            inputType = KeyboardType.Password,
                                            textFieldState = passwordTextFieldState,
                                            isPassword = true
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

data class PasswordOutputTransformation(val char: String) : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        replace(0, length, char.repeat(length))
    }
}

@Composable
fun FormField(
    modifier: Modifier,
    textFieldState: TextFieldState,
    text: String,
    inputType: KeyboardType,
    isPassword: Boolean = false
) {
    Column(modifier = modifier) {
        Text(text = text)
        BasicTextField(
            outputTransformation = if (isPassword) PasswordOutputTransformation("●") else null,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Unspecified,
                keyboardType = inputType),
            modifier = Modifier.fillMaxSize(),
            lineLimits = TextFieldLineLimits.SingleLine,
            decorator = { innerTextField ->
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)) {
                    Row(
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        innerTextField()
                    }
                }
            },
            state = textFieldState
        )
    }
}