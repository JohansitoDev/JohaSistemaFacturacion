package com.example.johafcturas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.johafcturas.model.User
import com.example.johafcturas.ui.theme.FinovaColors

enum class NavigationTab(val label: String, val icon: FinovaIconType) {
    DASHBOARD("Dashboard", FinovaIconType.HOME),
    INVOICES("Facturas", FinovaIconType.INVOICES),
    PRODUCTS("Productos", FinovaIconType.PRODUCTS),
    CLIENTS("Clientes", FinovaIconType.CLIENTS),
    ORGANIZATION("Organización & Roles", FinovaIconType.ORGANIZATION),
    SUPER_ADMIN("SuperAdmin", FinovaIconType.SUPER_ADMIN)
}

@Composable
fun FinovaTopBar(
    activeTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit,
    currentUser: User?,
    onLogout: () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val isDesktop = maxWidth > 800.dp

        if (isDesktop) {
            // --- Desktop Single Line Layout ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Brand Header
                BrandHeader(currentUser = currentUser)

                // Center Floating Navigation Pill Bar
                NavigationPillBar(
                    activeTab = activeTab,
                    onTabSelected = onTabSelected,
                    currentUser = currentUser
                )

                // Right User Controls
                UserControls(currentUser = currentUser, onLogout = onLogout)
            }
        } else {
            // --- Mobile / Tablet Stacked Layout ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BrandHeader(currentUser = currentUser)
                    UserControls(currentUser = currentUser, onLogout = onLogout)
                }

                // Scrollable Pill Navigation Bar for Mobile
                NavigationPillBar(
                    activeTab = activeTab,
                    onTabSelected = onTabSelected,
                    currentUser = currentUser,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun BrandHeader(currentUser: User?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "F",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Column {
            Text(
                text = "Finova",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = FinovaColors.TextPrimary
            )
            Text(
                text = currentUser?.companyId ?: "SuperAdmin Portal",
                fontSize = 10.sp,
                color = FinovaColors.TextSecondary
            )
        }
    }
}

@Composable
private fun NavigationPillBar(
    activeTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit,
    currentUser: User?,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = FinovaColors.DarkTealHeader,
        shape = RoundedCornerShape(26.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 6.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationTab.entries.forEach { tab ->
                if (tab == NavigationTab.SUPER_ADMIN && currentUser?.isSuperAdmin != true) {
                    return@forEach
                }

                val isSelected = tab == activeTab

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (isSelected) Color.White else Color.Transparent)
                        .clickable { onTabSelected(tab) }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FinovaIcon(
                            type = tab.icon,
                            tint = if (isSelected) FinovaColors.DarkTealHeader else Color.White.copy(alpha = 0.8f),
                            size = 15.dp
                        )
                        Text(
                            text = tab.label,
                            color = if (isSelected) FinovaColors.DarkTealHeader else Color.White.copy(alpha = 0.9f),
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UserControls(currentUser: User?, onLogout: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                FinovaIcon(FinovaIconType.BELL, tint = FinovaColors.TextPrimary, size = 16.dp)
            }
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(FinovaColors.CrimsonRed)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Text("2", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
            }
        }

        // User Avatar & Logout
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(FinovaColors.DarkTealHeader),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentUser?.fullName?.take(1)?.uppercase() ?: "U",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
            Text(
                text = currentUser?.fullName?.split(" ")?.firstOrNull() ?: "User",
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = FinovaColors.TextPrimary
            )
            IconButton(
                onClick = onLogout,
                modifier = Modifier.size(26.dp)
            ) {
                FinovaIcon(
                    type = FinovaIconType.LOGOUT,
                    tint = FinovaColors.CrimsonRed,
                    size = 14.dp
                )
            }
        }
    }
}
