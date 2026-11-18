package com.example.johafcturas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
    var isRegisterMode by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("admin_tech") }
    var password by remember { mutableStateOf("pass123") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(FinovaColors.PistachioBg),
        contentAlignment = Alignment.Center
    ) {
        val isWide = maxWidth > 800.dp

        if (isWide) {
            // Split View Layout matching reference screenshot
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(48.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Brand Column
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 48.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .background(FinovaColors.DarkTealHeader),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("F", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 38.sp)
                    }

                    Text(
                        text = "Finova",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 48.sp,
                        color = FinovaColors.DarkTealHeader
                    )

                    Text(
                        text = "Sistema de facturación multiplataforma sincronizado, con control de roles y permisos empresariales.",
                        fontSize = 15.sp,
                        color = FinovaColors.TextSecondary,
                        modifier = Modifier.width(360.dp)
                    )
                }

                // Right Form Column
                AuthFormCard(
                    isRegisterMode = isRegisterMode,
                    onToggleMode = { isRegisterMode = it; errorMessage = null },
                    email = email,
                    onEmailChange = { email = it; errorMessage = null },
                    password = password,
                    onPasswordChange = { password = it; errorMessage = null },
                    errorMessage = errorMessage,
                    onSubmit = {
                        val success = onLoginAttempt(email, password)
                        if (!success) {
                            errorMessage = "Credenciales incorrectas o cuenta desactivada."
                        }
                    },
                    modifier = Modifier.width(420.dp)
                )
            }
        } else {
            // Mobile Stacked Layout
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(FinovaColors.DarkTealHeader),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("F", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    }
                    Text(
                        text = "Finova",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 28.sp,
                        color = FinovaColors.DarkTealHeader
                    )
                }

                AuthFormCard(
                    isRegisterMode = isRegisterMode,
                    onToggleMode = { isRegisterMode = it; errorMessage = null },
                    email = email,
                    onEmailChange = { email = it; errorMessage = null },
                    password = password,
                    onPasswordChange = { password = it; errorMessage = null },
                    errorMessage = errorMessage,
                    onSubmit = {
                        val success = onLoginAttempt(email, password)
                        if (!success) {
                            errorMessage = "Credenciales incorrectas o cuenta desactivada."
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun AuthFormCard(
    isRegisterMode: Boolean,
    onToggleMode: (Boolean) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    errorMessage: String?,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shape = RoundedCornerShape(28.dp),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Title
            Text(
                text = if (isRegisterMode) "Crear cuenta" else "Iniciar seccion",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = FinovaColors.TextPrimary
            )

            // Segmented Pill Toggle (Iniciar seccion / Crear cuenta)
            Surface(
                color = FinovaColors.SurfaceTint,
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier.fillMaxWidth().height(46.dp)
            ) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Login Tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(24.dp))
                            .background(if (!isRegisterMode) FinovaColors.DarkTealHeader else Color.Transparent)
                            .clickable { onToggleMode(false) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Iniciar seccion",
                            color = if (!isRegisterMode) Color.White else FinovaColors.TextSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }

                    // Register Tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(24.dp))
                            .background(if (isRegisterMode) FinovaColors.DarkTealHeader else Color.Transparent)
                            .clickable { onToggleMode(true) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Crear cuenta",
                            color = if (isRegisterMode) Color.White else FinovaColors.TextSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            // Form Inputs
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Correo eletrónico", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = FinovaColors.TextSecondary)
                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    placeholder = { Text("Correo@gmail.com") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Contraseña", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = FinovaColors.TextSecondary)
                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    placeholder = { Text("••••••••••••••••") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )
            }

            errorMessage?.let { error ->
                Text(error, color = FinovaColors.CrimsonRed, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }

            // Quick Shortcut hint for testing
            Text(
                text = "💡 Acceso rápido: superadmin / admin123 o admin_tech / pass123",
                fontSize = 11.sp,
                color = FinovaColors.TextMuted
            )

            // Submit Button
            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = if (isRegisterMode) "Crear cuenta" else "Entrar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
        }
    }
}
