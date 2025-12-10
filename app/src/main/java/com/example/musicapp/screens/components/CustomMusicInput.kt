package com.example.musicapp.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicapp.ui.theme.Bluey
import com.example.musicapp.ui.theme.InputDark
import com.example.musicapp.ui.theme.LightGray
import com.example.musicapp.ui.theme.PlaceholderGray
import com.example.musicapp.ui.theme.PureWhite

@Composable
fun CustomMusicInput(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    val componentHeight = 72.dp
    val cornerRadius = 16.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(componentHeight + 4.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        // CAPA INFERIOR (El borde/sombra azul)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(componentHeight)
                .offset(y = 4.dp),
            shape = RoundedCornerShape(cornerRadius),
            color = Bluey
        ) {}

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(componentHeight),
            shape = RoundedCornerShape(cornerRadius),
            color = InputDark
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                val labelColor = if (isPassword) LightGray else PureWhite

                Text(
                    text = label,
                    color = labelColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        color = PureWhite,
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.padding(top = 4.dp)) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = PlaceholderGray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
    }
}