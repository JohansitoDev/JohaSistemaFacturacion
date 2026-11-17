package com.example.johafcturas.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class FinovaIconType {
    HOME, INVOICES, PRODUCTS, CLIENTS, ORGANIZATION, SUPER_ADMIN,
    SEARCH, BELL, HELP, LOGOUT, PLUS, BANK, PIGGY, BRIEFCASE,
    CARD, SEND, BILL, REFRESH, USER, SHIELD, CHECK, LOCK, EDIT
}

@Composable
fun FinovaIcon(
    type: FinovaIconType,
    tint: Color = Color.White,
    size: Dp = 20.dp,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = (w * 0.09f).coerceAtLeast(1.5f)

        when (type) {
            FinovaIconType.HOME -> {
                val path = Path().apply {
                    moveTo(w * 0.15f, h * 0.9f)
                    lineTo(w * 0.15f, h * 0.45f)
                    lineTo(w * 0.5f, h * 0.15f)
                    lineTo(w * 0.85f, h * 0.45f)
                    lineTo(w * 0.85f, h * 0.9f)
                    close()
                }
                drawPath(path, color = tint, style = Stroke(width = strokeWidth))
            }
            FinovaIconType.INVOICES -> {
                val path = Path().apply {
                    moveTo(w * 0.2f, h * 0.15f)
                    lineTo(w * 0.8f, h * 0.15f)
                    lineTo(w * 0.8f, h * 0.85f)
                    lineTo(w * 0.65f, h * 0.75f)
                    lineTo(w * 0.5f, h * 0.85f)
                    lineTo(w * 0.35f, h * 0.75f)
                    lineTo(w * 0.2f, h * 0.85f)
                    close()
                }
                drawPath(path, color = tint, style = Stroke(width = strokeWidth))
                drawLine(tint, Offset(w * 0.35f, h * 0.35f), Offset(w * 0.65f, h * 0.35f), strokeWidth)
                drawLine(tint, Offset(w * 0.35f, h * 0.52f), Offset(w * 0.65f, h * 0.52f), strokeWidth)
            }
            FinovaIconType.PRODUCTS -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(w * 0.2f, h * 0.2f),
                    size = Size(w * 0.6f, h * 0.6f),
                    cornerRadius = CornerRadius(w * 0.1f, h * 0.1f),
                    style = Stroke(width = strokeWidth)
                )
                drawLine(tint, Offset(w * 0.2f, h * 0.4f), Offset(w * 0.8f, h * 0.4f), strokeWidth)
            }
            FinovaIconType.CLIENTS -> {
                drawCircle(color = tint, radius = w * 0.22f, center = Offset(w * 0.5f, h * 0.32f), style = Stroke(width = strokeWidth))
                val path = Path().apply {
                    moveTo(w * 0.2f, h * 0.85f)
                    quadraticTo(w * 0.5f, h * 0.55f, w * 0.8f, h * 0.85f)
                }
                drawPath(path, color = tint, style = Stroke(width = strokeWidth))
            }
            FinovaIconType.ORGANIZATION -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(w * 0.2f, h * 0.3f),
                    size = Size(w * 0.6f, h * 0.55f),
                    cornerRadius = CornerRadius(w * 0.05f, h * 0.05f),
                    style = Stroke(width = strokeWidth)
                )
                drawRect(tint, topLeft = Offset(w * 0.35f, h * 0.15f), size = Size(w * 0.3f, h * 0.15f), style = Stroke(width = strokeWidth))
            }
            FinovaIconType.SUPER_ADMIN -> {
                val path = Path().apply {
                    moveTo(w * 0.5f, h * 0.15f)
                    lineTo(w * 0.82f, h * 0.32f)
                    lineTo(w * 0.82f, h * 0.62f)
                    lineTo(w * 0.5f, h * 0.85f)
                    lineTo(w * 0.18f, h * 0.62f)
                    lineTo(w * 0.18f, h * 0.32f)
                    close()
                }
                drawPath(path, color = tint, style = Stroke(width = strokeWidth))
            }
            FinovaIconType.SEARCH -> {
                drawCircle(color = tint, radius = w * 0.26f, center = Offset(w * 0.42f, h * 0.42f), style = Stroke(width = strokeWidth))
                drawLine(tint, Offset(w * 0.6f, h * 0.6f), Offset(w * 0.85f, h * 0.85f), strokeWidth)
            }
            FinovaIconType.BELL -> {
                val path = Path().apply {
                    moveTo(w * 0.25f, h * 0.7f)
                    lineTo(w * 0.75f, h * 0.7f)
                    lineTo(w * 0.68f, h * 0.4f)
                    quadraticTo(w * 0.68f, h * 0.2f, w * 0.5f, h * 0.2f)
                    quadraticTo(w * 0.32f, h * 0.2f, w * 0.32f, h * 0.4f)
                    close()
                }
                drawPath(path, color = tint, style = Stroke(width = strokeWidth))
                drawCircle(color = tint, radius = w * 0.08f, center = Offset(w * 0.5f, h * 0.82f))
            }
            FinovaIconType.HELP -> {
                drawCircle(color = tint, radius = w * 0.4f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(width = strokeWidth))
                drawCircle(color = tint, radius = w * 0.06f, center = Offset(w * 0.5f, h * 0.72f))
            }
            FinovaIconType.LOGOUT -> {
                drawLine(tint, Offset(w * 0.2f, h * 0.15f), Offset(w * 0.2f, h * 0.85f), strokeWidth)
                drawLine(tint, Offset(w * 0.2f, h * 0.5f), Offset(w * 0.75f, h * 0.5f), strokeWidth)
                val path = Path().apply {
                    moveTo(w * 0.55f, h * 0.3f)
                    lineTo(w * 0.8f, h * 0.5f)
                    lineTo(w * 0.55f, h * 0.7f)
                }
                drawPath(path, color = tint, style = Stroke(width = strokeWidth))
            }
            FinovaIconType.PLUS -> {
                drawLine(tint, Offset(w * 0.5f, h * 0.2f), Offset(w * 0.5f, h * 0.8f), strokeWidth * 1.3f)
                drawLine(tint, Offset(w * 0.2f, h * 0.5f), Offset(w * 0.8f, h * 0.5f), strokeWidth * 1.3f)
            }
            FinovaIconType.BANK -> {
                val path = Path().apply {
                    moveTo(w * 0.15f, h * 0.4f)
                    lineTo(w * 0.5f, h * 0.15f)
                    lineTo(w * 0.85f, h * 0.4f)
                    close()
                }
                drawPath(path, color = tint, style = Stroke(width = strokeWidth))
                drawLine(tint, Offset(w * 0.25f, h * 0.45f), Offset(w * 0.25f, h * 0.75f), strokeWidth)
                drawLine(tint, Offset(w * 0.5f, h * 0.45f), Offset(w * 0.5f, h * 0.75f), strokeWidth)
                drawLine(tint, Offset(w * 0.75f, h * 0.45f), Offset(w * 0.75f, h * 0.75f), strokeWidth)
                drawLine(tint, Offset(w * 0.15f, h * 0.8f), Offset(w * 0.85f, h * 0.8f), strokeWidth)
            }
            FinovaIconType.PIGGY -> {
                drawCircle(color = tint, radius = w * 0.32f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(width = strokeWidth))
                drawCircle(color = tint, radius = w * 0.05f, center = Offset(w * 0.38f, h * 0.42f))
            }
            FinovaIconType.BRIEFCASE -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(w * 0.15f, h * 0.35f),
                    size = Size(w * 0.7f, h * 0.5f),
                    cornerRadius = CornerRadius(w * 0.08f, h * 0.08f),
                    style = Stroke(width = strokeWidth)
                )
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(w * 0.35f, h * 0.18f),
                    size = Size(w * 0.3f, h * 0.17f),
                    cornerRadius = CornerRadius(w * 0.04f, h * 0.04f),
                    style = Stroke(width = strokeWidth)
                )
            }
            FinovaIconType.CARD -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(w * 0.15f, h * 0.25f),
                    size = Size(w * 0.7f, h * 0.5f),
                    cornerRadius = CornerRadius(w * 0.08f, h * 0.08f),
                    style = Stroke(width = strokeWidth)
                )
                drawLine(tint, Offset(w * 0.15f, h * 0.42f), Offset(w * 0.85f, h * 0.42f), strokeWidth)
            }
            FinovaIconType.SEND -> {
                val path = Path().apply {
                    moveTo(w * 0.15f, h * 0.5f)
                    lineTo(w * 0.85f, h * 0.15f)
                    lineTo(w * 0.5f, h * 0.85f)
                    lineTo(w * 0.4f, h * 0.6f)
                    close()
                }
                drawPath(path, color = tint, style = Stroke(width = strokeWidth))
            }
            FinovaIconType.BILL -> {
                drawRect(color = tint, topLeft = Offset(w * 0.2f, h * 0.2f), size = Size(w * 0.6f, h * 0.6f), style = Stroke(width = strokeWidth))
                drawLine(tint, Offset(w * 0.35f, h * 0.4f), Offset(w * 0.65f, h * 0.4f), strokeWidth)
            }
            FinovaIconType.REFRESH -> {
                drawCircle(color = tint, radius = w * 0.32f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(width = strokeWidth))
            }
            FinovaIconType.USER -> {
                drawCircle(color = tint, radius = w * 0.25f, center = Offset(w * 0.5f, h * 0.35f), style = Stroke(width = strokeWidth))
            }
            FinovaIconType.SHIELD -> {
                val path = Path().apply {
                    moveTo(w * 0.5f, h * 0.15f)
                    lineTo(w * 0.85f, h * 0.3f)
                    lineTo(w * 0.85f, h * 0.6f)
                    quadraticTo(w * 0.5f, h * 0.9f, w * 0.5f, h * 0.9f)
                    quadraticTo(w * 0.15f, h * 0.6f, w * 0.15f, h * 0.3f)
                    close()
                }
                drawPath(path, color = tint, style = Stroke(width = strokeWidth))
            }
            FinovaIconType.CHECK -> {
                val path = Path().apply {
                    moveTo(w * 0.2f, h * 0.5f)
                    lineTo(w * 0.42f, h * 0.72f)
                    lineTo(w * 0.8f, h * 0.28f)
                }
                drawPath(path, color = tint, style = Stroke(width = strokeWidth * 1.3f))
            }
            FinovaIconType.LOCK -> {
                drawRoundRect(
                    color = tint,
                    topLeft = Offset(w * 0.22f, h * 0.45f),
                    size = Size(w * 0.56f, h * 0.42f),
                    cornerRadius = CornerRadius(w * 0.08f, h * 0.08f),
                    style = Stroke(width = strokeWidth)
                )
                val path = Path().apply {
                    moveTo(w * 0.33f, h * 0.45f)
                    lineTo(w * 0.33f, h * 0.28f)
                    quadraticTo(w * 0.5f, h * 0.12f, w * 0.67f, h * 0.28f)
                    lineTo(w * 0.67f, h * 0.45f)
                }
                drawPath(path, color = tint, style = Stroke(width = strokeWidth))
            }
            FinovaIconType.EDIT -> {
                drawLine(tint, Offset(w * 0.25f, h * 0.75f), Offset(w * 0.75f, h * 0.25f), strokeWidth)
                drawLine(tint, Offset(w * 0.2f, h * 0.8f), Offset(w * 0.4f, h * 0.8f), strokeWidth)
            }
        }
    }
}
