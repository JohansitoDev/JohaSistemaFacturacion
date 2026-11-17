package com.example.johafcturas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.johafcturas.model.User
import com.example.johafcturas.ui.theme.FinovaColors

enum class NavigationTab(val label: String, val icon: ImageVector) {
    DASHBOARD("Dashboard", Icons.Default.Home),
    INVOICES("Facturas", Icons.Default.Receipt),
    PRODUCTS("Productos", Icons.Default.Inventory),
    CLIENTS("Clientes", Icons.Default.People),
    ORGANIZATION("Organización & Roles", Icons.Default.CorporateFare),
    SUPER_ADMIN("SuperAdmin", Icons.Default.AdminPanelSettings)
}

@Composable
fun FinovaTopBar(
    activeTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit,
    currentUser: User?,
    onLogout: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- Left Brand Header ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "F",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
            Column {
                Text(
                    text = "JohaFcturas",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = FinovaColors.TextPrimary
                )
                Text(
                    text = currentUser?.companyId ?: "SuperAdmin Portal",
                    fontSize = 11.sp,
                    color = FinovaColors.TextSecondary
                )
            }
        }

        // --- Center Floating Navigation Pill Bar ---
        Surface(
            color = FinovaColors.DarkTealHeader,
            shape = RoundedCornerShape(30.dp),
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationTab.entries.forEach { tab ->
                    // Hide SuperAdmin tab if current user is not SuperAdmin
                    if (tab == NavigationTab.SUPER_ADMIN && currentUser?.isSuperAdmin != true) {
                        return@forEach
                    }

                    val isSelected = tab == activeTab

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) Color.White else Color.Transparent)
                            .clickable { onTabSelected(tab) }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.label,
                                tint = if (isSelected) FinovaColors.DarkTealHeader else Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = tab.label,
                                color = if (isSelected) FinovaColors.DarkTealHeader else Color.White.copy(alpha = 0.9f),
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // --- Right Action Controls & User Avatar ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = FinovaColors.TextPrimary, modifier = Modifier.size(20.dp))
            }

            Box {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = FinovaColors.TextPrimary, modifier = Modifier.size(20.dp))
                }
                // Notification Badge counter
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(FinovaColors.CrimsonRed)
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text("2", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }
            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Icon(Icons.Default.HelpOutline, contentDescription = "Help", tint = FinovaColors.TextPrimary, modifier = Modifier.size(20.dp))
            }

            Spacer(Modifier.width(4.dp))

            // User Profile Chip
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(FinovaColors.DarkTealHeader),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentUser?.fullName?.take(1)?.uppercase() ?: "U",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Column {
                    Text(
                        text = currentUser?.fullName ?: "Invitado",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = FinovaColors.TextPrimary
                    )
                    Text(
                        text = if (currentUser?.isSuperAdmin == true) "SuperAdmin" else "Admin",
                        fontSize = 10.sp,
                        color = FinovaColors.EmeraldGreen
                    )
                }
                IconButton(
                    onClick = onLogout,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Cerrar Sesión",
                        tint = FinovaColors.CrimsonRed,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
