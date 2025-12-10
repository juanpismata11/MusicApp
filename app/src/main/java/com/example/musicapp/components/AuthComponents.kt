package com.example.musicapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.musicapp.ui.theme.*

@Composable
fun AuthTextField(
    label: String,
    placeholder: String,
    isPassword: Boolean = false
) {
    var text by remember { mutableStateOf("") }

    Column( modifier = Modifier
        .fillMaxSize())
    {
        Text(
            text = label,
            color = DarkBlue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 4.dp, bottom = 4.dp)
        )

        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(placeholder, color= PlaceholderGray)},
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = InputDark,
                unfocusedContainerColor = InputDark,
                cursorColor = Bluey,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        )
    }
}

@Composable
fun BlueButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Bluey
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold, color = PureWhite)
    }
}

// Prueba