package com.example.johafcturas.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object FinovaColors {
    val PistachioBg = Color(0xFFEAF4ED)
    val DarkTealHeader = Color(0xFF153B36)
    val DarkTealPillActive = Color(0xFF285650)
    val DarkCardBg = Color(0xFF111716)
    val WhiteCardBg = Color(0xFFFFFFFF)
    val SurfaceTint = Color(0xFFF4FAF6)
    
    val EmeraldGreen = Color(0xFF22C55E)
    val EmeraldGreenLight = Color(0xFFDCFCE7)
    val CrimsonRed = Color(0xFFEF4444)
    val CrimsonRedLight = Color(0xFFFEE2E2)
    
    val TealAccent = Color(0xFF2DD4BF)
    val GoldAccent = Color(0xFFEAB308)
    val PurpleAccent = Color(0xFFA855F7)
    val PinkAccent = Color(0xFFEC4899)
    val BlueAccent = Color(0xFF3B82F6)
    
    val TextPrimary = Color(0xFF0F172A)
    val TextSecondary = Color(0xFF64748B)
    val TextMuted = Color(0xFF94A3B8)
    val BorderLight = Color(0xFFE2E8F0)
}

val FinovaShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp)
)

private val LightColorScheme = lightColorScheme(
    primary = FinovaColors.DarkTealHeader,
    onPrimary = Color.White,
    primaryContainer = FinovaColors.PistachioBg,
    onPrimaryContainer = FinovaColors.TextPrimary,
    secondary = FinovaColors.EmeraldGreen,
    onSecondary = Color.White,
    background = FinovaColors.PistachioBg,
    onBackground = FinovaColors.TextPrimary,
    surface = FinovaColors.WhiteCardBg,
    onSurface = FinovaColors.TextPrimary,
    surfaceVariant = FinovaColors.SurfaceTint,
    onSurfaceVariant = FinovaColors.TextSecondary,
    outline = FinovaColors.BorderLight
)

@Composable
fun FinovaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        shapes = FinovaShapes,
        content = content
    )
}
