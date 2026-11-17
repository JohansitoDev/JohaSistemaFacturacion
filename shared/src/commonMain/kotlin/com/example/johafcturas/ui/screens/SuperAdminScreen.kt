package com.example.johafcturas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.johafcturas.model.*
import com.example.johafcturas.ui.components.FinovaIcon
import com.example.johafcturas.ui.components.FinovaIconType
import com.example.johafcturas.ui.components.SoftDeleteActiveBadge
import com.example.johafcturas.ui.theme.FinovaColors

@Composable
fun SuperAdminScreen(
    companies: List<Company>,
    users: List<User>,
    onAddCompany: (Company, User, UserRole) -> Unit,
    onToggleCompanyStatus: (companyId: String) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Portal Exclusivo SuperAdmin", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = FinovaColors.TextPrimary)
                Text("Registro de nuevas empresas cliente y asignación de credenciales de administradores", fontSize = 13.sp, color = FinovaColors.TextSecondary)
            }

            Button(
                onClick = { showAddDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FinovaIcon(FinovaIconType.SUPER_ADMIN, tint = Color.White, size = 18.dp)
                    Text("+ Registrar Empresa", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }

        // System Global Stats Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                modifier = Modifier.weight(1f),
                color = Color.White,
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Total Empresas Registradas", fontSize = 12.sp, color = FinovaColors.TextSecondary)
                    Text("${companies.size}", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = FinovaColors.DarkTealHeader)
                }
            }

            Surface(
                modifier = Modifier.weight(1f),
                color = Color.White,
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Empresas Activas", fontSize = 12.sp, color = FinovaColors.TextSecondary)
                    Text("${companies.count { it.isActive }}", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = FinovaColors.EmeraldGreen)
                }
            }

            Surface(
                modifier = Modifier.weight(1f),
                color = Color.White,
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Empresas Desactivadas", fontSize = 12.sp, color = FinovaColors.TextSecondary)
                    Text("${companies.count { !it.isActive }}", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = FinovaColors.CrimsonRed)
                }
            }
        }

        // Companies List
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 2.dp
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(companies) { comp ->
                    val companyAdmin = users.find { it.id == comp.adminUserId }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (comp.isActive) FinovaColors.SurfaceTint else Color.LightGray.copy(alpha = 0.2f))
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(FinovaColors.DarkTealHeader),
                                contentAlignment = Alignment.Center
                            ) {
                                FinovaIcon(FinovaIconType.ORGANIZATION, tint = Color.White, size = 22.dp)
                            }

                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(comp.name, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = FinovaColors.TextPrimary)
                                Text("RNC/NIT: ${comp.rnc} | Email: ${comp.email}", fontSize = 12.sp, color = FinovaColors.TextSecondary)
                                Text(
                                    text = "Admin Asignado: ${companyAdmin?.fullName ?: "N/A"} (${companyAdmin?.username ?: "N/A"})",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FinovaColors.DarkTealHeader
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            SoftDeleteActiveBadge(comp.isActive)

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White)
                                    .clickable { onToggleCompanyStatus(comp.id) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = if (comp.isActive) "Desactivar Empresa" else "Reactivar",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (comp.isActive) FinovaColors.CrimsonRed else FinovaColors.EmeraldGreen
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddCompanyModal(
            onDismiss = { showAddDialog = false },
            onConfirm = { comp, adminUser, adminRole ->
                onAddCompany(comp, adminUser, adminRole)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun AddCompanyModal(
    onDismiss: () -> Unit,
    onConfirm: (Company, User, UserRole) -> Unit
) {
    var companyName by remember { mutableStateOf("") }
    var rnc by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var adminName by remember { mutableStateOf("") }
    var adminUser by remember { mutableStateOf("") }
    var adminPass by remember { mutableStateOf("pass123") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Registrar Nueva Empresa (SuperAdmin)", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Datos de la Empresa:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = FinovaColors.DarkTealHeader)
                OutlinedTextField(value = companyName, onValueChange = { companyName = it }, label = { Text("Nombre Comercial de Empresa") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = rnc, onValueChange = { rnc = it }, label = { Text("RNC / Identificación Fiscal") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo Institucional") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth())

                HorizontalDivider()

                Text("Credenciales Administrador Inicial:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = FinovaColors.DarkTealHeader)
                OutlinedTextField(value = adminName, onValueChange = { adminName = it }, label = { Text("Nombre Completo Administrador") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = adminUser, onValueChange = { adminUser = it }, label = { Text("Nombre de Usuario Admin") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = adminPass, onValueChange = { adminPass = it }, label = { Text("Contraseña Admin") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val compId = "comp_${(100..999).random()}"
                    val adminUserId = "usr_admin_${(100..999).random()}"
                    val adminRoleId = "role_admin_${(100..999).random()}"

                    val newCompany = Company(
                        id = compId,
                        name = if (companyName.isNotBlank()) companyName else "Nueva Empresa S.A.",
                        rnc = rnc,
                        address = address,
                        phone = "+1 (809) 555-0000",
                        email = email,
                        adminUserId = adminUserId,
                        isActive = true,
                        createdAt = "2024-05-01"
                    )

                    val newAdminRole = UserRole(
                        id = adminRoleId,
                        name = "Administrador de Empresa",
                        description = "Rol Administrador creado por SuperAdmin",
                        companyId = compId,
                        permissions = AppPermission.entries.filter { it != AppPermission.MANAGE_COMPANIES }.toSet(),
                        isActive = true,
                        isSystemRole = true
                    )

                    val newAdminUser = User(
                        id = adminUserId,
                        username = if (adminUser.isNotBlank()) adminUser else "admin_new",
                        passwordHash = adminPass,
                        fullName = if (adminName.isNotBlank()) adminName else "Admin General",
                        email = email,
                        roleId = adminRoleId,
                        companyId = compId,
                        isSuperAdmin = false,
                        isActive = true
                    )

                    onConfirm(newCompany, newAdminUser, newAdminRole)
                },
                colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader)
            ) {
                Text("Registrar Empresa y Credenciales")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
