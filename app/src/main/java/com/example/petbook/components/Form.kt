package com.example.petbook.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

data class PasswordOutputTransformation(val char: String) : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        replace(0, length, char.repeat(length))
    }
}

@Composable
fun FormField(
    modifier: Modifier,
    textFieldState: TextFieldState,
    text: String = "",
    inputType: KeyboardType,
    isPassword: Boolean = false
) {
    Column(modifier = modifier) {
        Text(text = text)
        BasicTextField(
            outputTransformation = if (isPassword) PasswordOutputTransformation("●") else null,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Unspecified,
                keyboardType = inputType
            ),
            modifier = Modifier.fillMaxSize(),
            lineLimits = TextFieldLineLimits.SingleLine,
            decorator = { innerTextField ->
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        innerTextField()
                    }
                }
            },
            state = textFieldState
        )
    }
}

@Composable
fun FormFieldArea(
    modifier: Modifier,
    textFieldState: TextFieldState,
    text: String = "",
    inputType: KeyboardType,
    isPassword: Boolean = false,
    maxHeightInLines: Int
) {
    Column(modifier = modifier) {
        Text(text = text)
        BasicTextField(
            outputTransformation = if (isPassword) PasswordOutputTransformation("●") else null,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Unspecified,
                keyboardType = inputType
            ),
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 128.dp),
            lineLimits = TextFieldLineLimits.MultiLine(maxHeightInLines = maxHeightInLines),
            decorator = { innerTextField ->
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        innerTextField()
                    }
                }
            },
            state = textFieldState
        )
    }
}