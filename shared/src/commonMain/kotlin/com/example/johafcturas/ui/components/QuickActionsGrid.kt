package com.example.johafcturas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.johafcturas.ui.theme.FinovaColors

enum class QuickActionType(
    val label: String,
    val icon: FinovaIconType,
    val color: Color
) {
    NUEVA_FACTURA("Send Money", FinovaIconType.SEND, FinovaColors.EmeraldGreen),
    AGREGAR_PRODUCTO("Add Funds", FinovaIconType.CARD, FinovaColors.TealAccent),
    NUEVO_CLIENTE("Pay Bills", FinovaIconType.BILL, FinovaColors.GoldAccent),
    ROLES_PERMISOS("Exchange", FinovaIconType.ORGANIZATION, FinovaColors.PurpleAccent)
}

@Composable
fun QuickActionsGridCard(
    onActionClicked: (QuickActionType) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = FinovaColors.DarkCardBg,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header: Title & Green Plus Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Quick Actions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(FinovaColors.EmeraldGreen)
                        .clickable { onActionClicked(QuickActionType.NUEVA_FACTURA) },
                    contentAlignment = Alignment.Center
                ) {
                    FinovaIcon(type = FinovaIconType.PLUS, tint = Color.Black, size = 14.dp)
                }
            }

            // 4 Quick Action Tiles Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuickActionType.entries.forEach { action ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onActionClicked(action) }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(action.color.copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            FinovaIcon(type = action.icon, tint = action.color, size = 20.dp)
                        }
                        Text(
                            text = action.label,
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
