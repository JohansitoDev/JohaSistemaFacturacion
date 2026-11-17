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
fun OrganizationScreen(
    departments: List<Department>,
    roles: List<UserRole>,
    users: List<User>,
    onAddDepartment: (Department) -> Unit,
    onToggleDepartmentStatus: (deptId: String) -> Unit,
    onAddRole: (UserRole) -> Unit,
    onToggleRoleStatus: (roleId: String) -> Unit,
    onAddUser: (User) -> Unit,
    onToggleUserStatus: (userId: String) -> Unit
) {
    var activeSubTab by remember { mutableStateOf(0) } // 0: Secciones/Deptos, 1: Roles & Permisos, 2: Personal

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        Column {
            Text("Gestión de Organización, Roles y Personal", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = FinovaColors.TextPrimary)
            Text("Configura los departamentos de la empresa, define roles con permisos específicos y gestiona el estado del personal", fontSize = 13.sp, color = FinovaColors.TextSecondary)
        }

        // Navigation Sub-Tabs
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(20.dp),
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Secciones / Departamentos", "Roles & Matriz de Permisos", "Cuentas de Personal").forEachIndexed { idx, title ->
                    val isSelected = activeSubTab == idx
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) FinovaColors.DarkTealHeader else Color.Transparent)
                            .clickable { activeSubTab = idx }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            color = if (isSelected) Color.White else FinovaColors.TextSecondary,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Sub-Tab Content
        when (activeSubTab) {
            0 -> DepartmentsTab(departments, onAddDepartment, onToggleDepartmentStatus)
            1 -> RolesTab(roles, onAddRole, onToggleRoleStatus)
            2 -> StaffTab(users, roles, departments, onAddUser, onToggleUserStatus)
        }
    }
}

// --- 1. Departments / Sections Tab ---
@Composable
private fun DepartmentsTab(
    departments: List<Department>,
    onAddDepartment: (Department) -> Unit,
    onToggleStatus: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Secciones de Trabajo", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = FinovaColors.TextPrimary)
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("+ Agregar Sección", fontWeight = FontWeight.Bold)
            }
        }

        Surface(color = Color.White, shape = RoundedCornerShape(24.dp), shadowElevation = 2.dp) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(departments) { dept ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(FinovaColors.SurfaceTint)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(dept.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = FinovaColors.TextPrimary)
                            Text(dept.description, fontSize = 12.sp, color = FinovaColors.TextSecondary)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            SoftDeleteActiveBadge(dept.isActive)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White)
                                    .clickable { onToggleStatus(dept.id) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = if (dept.isActive) "Desactivar Sección" else "Reactivar",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (dept.isActive) FinovaColors.CrimsonRed else FinovaColors.EmeraldGreen
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Nueva Sección / Departamento") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre (ej. Ventas, Caja, Almacén)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val d = Department(id = "dept_${(100..999).random()}", name = name, description = desc, companyId = "comp_technova", isActive = true)
                        onAddDepartment(d)
                        name = ""; desc = ""; showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader)
                ) { Text("Crear Sección") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancelar") } }
        )
    }
}

// --- 2. Roles & Permissions Tab ---
@Composable
private fun RolesTab(
    roles: List<UserRole>,
    onAddRole: (UserRole) -> Unit,
    onToggleStatus: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var roleName by remember { mutableStateOf("") }
    var roleDesc by remember { mutableStateOf("") }
    var selectedPermissions by remember { mutableStateOf(setOf(AppPermission.VIEW_DASHBOARD, AppPermission.CREATE_INVOICES)) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Matriz de Roles y Permisos (No se eliminan, solo desactivan)", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = FinovaColors.TextPrimary)
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("+ Nuevo Rol", fontWeight = FontWeight.Bold)
            }
        }

        Surface(color = Color.White, shape = RoundedCornerShape(24.dp), shadowElevation = 2.dp) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(roles) { role ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(FinovaColors.SurfaceTint)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(role.name, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = FinovaColors.TextPrimary)
                                if (role.isSystemRole) {
                                    Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(FinovaColors.DarkTealHeader).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                        Text("Sistema", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                SoftDeleteActiveBadge(role.isActive)
                                if (!role.isSystemRole) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color.White)
                                            .clickable { onToggleStatus(role.id) }
                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = if (role.isActive) "Desactivar Rol" else "Reactivar",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (role.isActive) FinovaColors.CrimsonRed else FinovaColors.EmeraldGreen
                                        )
                                    }
                                }
                            }
                        }

                        Text(role.description, fontSize = 12.sp, color = FinovaColors.TextSecondary)

                        Text("Permisos asignados:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = FinovaColors.TextPrimary)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                            role.permissions.forEach { perm ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(perm.label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = FinovaColors.TextPrimary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Crear Nuevo Rol de Personal") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = roleName, onValueChange = { roleName = it }, label = { Text("Nombre del Rol (ej. Vendedor, Cajero)") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = roleDesc, onValueChange = { roleDesc = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
                    Text("Selecciona Permisos:", fontWeight = FontWeight.Bold, fontSize = 12.sp)

                    AppPermission.entries.filter { it != AppPermission.MANAGE_COMPANIES }.forEach { perm ->
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Checkbox(
                                checked = selectedPermissions.contains(perm),
                                onCheckedChange = { isChecked ->
                                    selectedPermissions = if (isChecked) selectedPermissions + perm else selectedPermissions - perm
                                }
                            )
                            Text(perm.label, fontSize = 12.sp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val r = UserRole(
                            id = "role_${(100..999).random()}",
                            name = roleName,
                            description = roleDesc,
                            companyId = "comp_technova",
                            permissions = selectedPermissions,
                            isActive = true,
                            isSystemRole = false
                        )
                        onAddRole(r)
                        roleName = ""; roleDesc = ""; showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader)
                ) { Text("Guardar Rol") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancelar") } }
        )
    }
}

// --- 3. Staff Accounts Tab ---
@Composable
private fun StaffTab(
    users: List<User>,
    roles: List<UserRole>,
    departments: List<Department>,
    onAddUser: (User) -> Unit,
    onToggleStatus: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("pass123") }
    var email by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf(roles.firstOrNull()?.id ?: "") }
    var selectedDept by remember { mutableStateOf(departments.firstOrNull()?.id ?: "") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Cuentas de Personal de la Empresa", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = FinovaColors.TextPrimary)
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("+ Crear Usuario Personal", fontWeight = FontWeight.Bold)
            }
        }

        Surface(color = Color.White, shape = RoundedCornerShape(24.dp), shadowElevation = 2.dp) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(users.filter { !it.isSuperAdmin }) { usr ->
                    val roleObj = roles.find { it.id == usr.roleId }
                    val deptObj = departments.find { it.id == usr.departmentId }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(FinovaColors.SurfaceTint)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(FinovaColors.DarkTealHeader),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(usr.fullName.take(1).uppercase(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }

                            Column {
                                Text(usr.fullName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = FinovaColors.TextPrimary)
                                Text("${usr.email} | Usuario: ${usr.username}", fontSize = 11.sp, color = FinovaColors.TextSecondary)
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 4.dp)) {
                                    Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(Color.White).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                        Text(roleObj?.name ?: "Sin Rol", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = FinovaColors.DarkTealHeader)
                                    }
                                    Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(Color.White).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                        Text(deptObj?.name ?: "Sin Sección", fontSize = 10.sp, color = FinovaColors.TextSecondary)
                                    }
                                }
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            SoftDeleteActiveBadge(usr.isActive)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White)
                                    .clickable { onToggleStatus(usr.id) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = if (usr.isActive) "Desactivar Personal" else "Reactivar",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (usr.isActive) FinovaColors.CrimsonRed else FinovaColors.EmeraldGreen
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Nuevo Personal") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Nombre Completo") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Nombre de Usuario") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo Electrónico") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Contraseña Provisoria") }, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val u = User(
                            id = "usr_${(100..999).random()}",
                            username = username,
                            passwordHash = pass,
                            fullName = fullName,
                            email = email,
                            roleId = selectedRole.ifEmpty { roles.firstOrNull()?.id ?: "role_sales" },
                            departmentId = selectedDept.ifEmpty { departments.firstOrNull()?.id ?: "dept_1" },
                            companyId = "comp_technova",
                            isSuperAdmin = false,
                            isActive = true
                        )
                        onAddUser(u)
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader)
                ) { Text("Crear Cuenta Personal") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancelar") } }
        )
    }
}
