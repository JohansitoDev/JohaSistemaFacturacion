package com.example.johafcturas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.johafcturas.model.InvoiceStatus
import com.example.johafcturas.ui.theme.FinovaColors

@Composable
fun InvoiceStatusBadge(status: InvoiceStatus) {
    val (bgColor, textColor) = when (status) {
        InvoiceStatus.PAID -> Pair(FinovaColors.EmeraldGreenLight, FinovaColors.EmeraldGreen)
        InvoiceStatus.PENDING -> Pair(FinovaColors.GoldAccent.copy(alpha = 0.15f), FinovaColors.GoldAccent)
        InvoiceStatus.OVERDUE -> Pair(FinovaColors.CrimsonRedLight, FinovaColors.CrimsonRed)
        InvoiceStatus.CANCELLED -> Pair(FinovaColors.BorderLight, FinovaColors.TextMuted)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.label,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SoftDeleteActiveBadge(isActive: Boolean) {
    val (bgColor, textColor, label) = if (isActive) {
        Triple(FinovaColors.EmeraldGreenLight, FinovaColors.EmeraldGreen, "Activo")
    } else {
        Triple(FinovaColors.BorderLight, FinovaColors.TextMuted, "Desactivado")
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
