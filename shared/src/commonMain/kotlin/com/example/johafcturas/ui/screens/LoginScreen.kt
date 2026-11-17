package com.example.johafcturas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.johafcturas.ui.components.FinovaIcon
import com.example.johafcturas.ui.components.FinovaIconType
import com.example.johafcturas.ui.theme.FinovaColors

@Composable
fun LoginScreen(
    onLoginAttempt: (username: String, pass: String) -> Boolean
) {
    var username by remember { mutableStateOf("admin_tech") }
    var password by remember { mutableStateOf("pass123") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FinovaColors.PistachioBg),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .width(460.dp)
                .padding(24.dp),
            color = Color.White,
            shape = RoundedCornerShape(28.dp),
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Header Logo
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(FinovaColors.DarkTealHeader),
                    contentAlignment = Alignment.Center
                ) {
                    Text("F", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 28.sp)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Bienvenido a JohaFcturas",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 22.sp,
                        color = FinovaColors.TextPrimary
                    )
                    Text(
                        text = "Sistema Multi-Empresa Multiplataforma",
                        fontSize = 13.sp,
                        color = FinovaColors.TextSecondary
                    )
                }

                // Quick Login Selector Chips
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            username = "superadmin"
                            password = "admin123"
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("SuperAdmin", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = {
                            username = "admin_tech"
                            password = "pass123"
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Admin Empresa", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Input Fields
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it; errorMessage = null },
                    label = { Text("Usuario / Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; errorMessage = null },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                errorMessage?.let { error ->
                    Text(error, color = FinovaColors.CrimsonRed, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                // Submit Button
                Button(
                    onClick = {
                        val success = onLoginAttempt(username, password)
                        if (!success) {
                            errorMessage = "Credenciales inválidas o cuenta desactivada."
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Iniciar Sesión", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        FinovaIcon(type = FinovaIconType.LOCK, tint = Color.White, size = 16.dp)
                    }
                }
            }
        }
    }
}
