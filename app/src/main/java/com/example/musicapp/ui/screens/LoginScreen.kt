package com.example.musicapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicapp.ui.theme.*

@Composable
fun LoginScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(
                    LightBackground,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .height(40.dp)
            )

            Text(
                text = "Bienvenido de Nuevo!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                ),
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

            AuthTextField(
                label = "Email",
                placeholder = "usuario@correo.com"
            )

            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )

            AuthTextField(
                label = "Contraseña",
                placeholder = "******",
                isPassword = true
            )
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            Text(
                text = "¿Olvidaste tu contraseña?",
                color = DarkBlue,
                modifier = Modifier
                    .align(Alignment.End),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(
                modifier = Modifier
                    .height(25.dp)
            )

            BlueButton(text = "Iniciar Sesión") {
                navController.navigate("home")
            }
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

            TextButton(onClick = { navController.navigate("register") }) {
                Text(
                    text = "¿No tienes una cuanta? Regístrate",
                    color = LinkBlue,
                )
            }
        }
    }
}