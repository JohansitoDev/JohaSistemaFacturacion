package com.example.johafcturas.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.johafcturas.model.SpendingCategory
import com.example.johafcturas.ui.theme.FinovaColors

@Composable
fun DonutChartCard(
    categories: List<SpendingCategory>,
    totalAmount: Double = 7250.00,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Spending Breakdown",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = FinovaColors.TextPrimary
                )

                Surface(
                    color = FinovaColors.SurfaceTint,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("This Month", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = FinovaColors.TextPrimary)
                        Text("▾", fontSize = 10.sp, color = FinovaColors.TextSecondary)
                    }
                }
            }

            // Donut & Legend Content (Responsive Row / Flow)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Donut Canvas with Center Text
                Box(
                    modifier = Modifier.size(110.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidth = 20.dp.toPx()
                        var startAngle = -90f

                        categories.forEach { item ->
                            val sweepAngle = (item.percentage / 100f) * 360f
                            drawArc(
                                color = Color(item.colorHex),
                                startAngle = startAngle,
                                sweepAngle = sweepAngle,
                                useCenter = false,
                                style = Stroke(width = strokeWidth)
                            )
                            startAngle += sweepAngle
                        }
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$${totalAmount.toInt()}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = FinovaColors.TextPrimary
                        )
                        Text(
                            text = "Total",
                            fontSize = 10.sp,
                            color = FinovaColors.TextSecondary
                        )
                    }
                }

                // Legend Column
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    categories.forEach { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(Color(item.colorHex))
                            )
                            Text(
                                text = item.category,
                                fontSize = 11.sp,
                                color = FinovaColors.TextSecondary,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "${item.percentage.toInt()}%",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = FinovaColors.TextPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}
