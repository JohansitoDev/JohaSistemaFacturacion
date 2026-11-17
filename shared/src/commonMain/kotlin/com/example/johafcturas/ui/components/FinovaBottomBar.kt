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

@Composable
fun FinovaBottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Pill 1: Active Users
        BottomStatPill(
            modifier = Modifier.weight(1f),
            icon = FinovaIconType.CLIENTS,
            value = "2.4M+",
            label = "Active Users"
        )

        // Pill 2: Transactions Processed
        BottomStatPill(
            modifier = Modifier.weight(1.5f),
            icon = FinovaIconType.BANK,
            value = "$850 Cr+",
            label = "Transactions Processed"
        )

        // Pill 3: Security Status
        BottomStatPill(
            modifier = Modifier.weight(1f),
            icon = FinovaIconType.SHIELD,
            value = "99.9%",
            label = "Secure & Reliable"
        )
    }
}

@Composable
private fun BottomStatPill(
    modifier: Modifier = Modifier,
    icon: FinovaIconType,
    value: String,
    label: String
) {
    Surface(
        modifier = modifier,
        color = FinovaColors.DarkTealHeader,
        shape = RoundedCornerShape(30.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    FinovaIcon(type = icon, tint = Color.White, size = 18.dp)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = value,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = label,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "→",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}
