package com.example.johafcturas.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.johafcturas.model.OverviewDataPoint
import com.example.johafcturas.ui.theme.FinovaColors

@Composable
fun OverviewChartCard(
    dataPoints: List<OverviewDataPoint>,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableStateOf(3) } // Default selected: 18 Apr

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Row: Title, Totals, Filter Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Overview",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = FinovaColors.TextPrimary
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Income Summary Pill
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(modifier = Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(FinovaColors.EmeraldGreen))
                        Text("Income", fontSize = 12.sp, color = FinovaColors.TextSecondary)
                        Text("$18,750.00", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FinovaColors.TextPrimary)
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(FinovaColors.EmeraldGreen.copy(alpha = 0.15f)).padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("↑ 8.4%", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = FinovaColors.EmeraldGreen)
                        }
                    }

                    // Expense Summary Pill
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(modifier = Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(FinovaColors.BlueAccent))
                        Text("Expense", fontSize = 12.sp, color = FinovaColors.TextSecondary)
                        Text("$7,250.00", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FinovaColors.TextPrimary)
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(FinovaColors.CrimsonRed.copy(alpha = 0.15f)).padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("↑ 3.2%", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = FinovaColors.CrimsonRed)
                        }
                    }

                    // Month Filter Dropdown Pill
                    Surface(
                        color = FinovaColors.SurfaceTint,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("This Month", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = FinovaColors.TextPrimary)
                            Text("▾", fontSize = 11.sp, color = FinovaColors.TextSecondary)
                        }
                    }
                }
            }

            // Canvas Line & Area Chart with curved path & Tooltip
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                val activePoint = dataPoints.getOrNull(selectedIndex)

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            selectedIndex = (selectedIndex + 1) % dataPoints.size
                        }
                ) {
                    val width = size.width
                    val height = size.height
                    val maxVal = 20000f

                    // Grid lines & Y-Labels
                    val gridSteps = listOf("20k", "15k", "10k", "5k", "0")
                    gridSteps.forEachIndexed { i, label ->
                        val y = (height / 4) * i
                        drawLine(
                            color = FinovaColors.BorderLight,
                            start = Offset(0f, y),
                            end = Offset(width, y),
                            strokeWidth = 1f
                        )
                    }

                    if (dataPoints.isEmpty()) return@Canvas

                    val stepX = width / (dataPoints.size - 1)

                    val incomePath = Path()
                    val expensePath = Path()

                    val incomeFillPath = Path()
                    val expenseFillPath = Path()

                    dataPoints.forEachIndexed { i, point ->
                        val x = i * stepX
                        val incomeY = height - (point.income.toFloat() / maxVal * height)
                        val expenseY = height - (point.expense.toFloat() / maxVal * height)

                        if (i == 0) {
                            incomePath.moveTo(x, incomeY)
                            expensePath.moveTo(x, expenseY)

                            incomeFillPath.moveTo(x, height)
                            incomeFillPath.lineTo(x, incomeY)

                            expenseFillPath.moveTo(x, height)
                            expenseFillPath.lineTo(x, expenseY)
                        } else {
                            val prevX = (i - 1) * stepX
                            val prevIncomeY = height - (dataPoints[i - 1].income.toFloat() / maxVal * height)
                            val prevExpenseY = height - (dataPoints[i - 1].expense.toFloat() / maxVal * height)

                            val controlX1 = prevX + (stepX / 2)
                            val controlX2 = x - (stepX / 2)

                            incomePath.cubicTo(controlX1, prevIncomeY, controlX2, incomeY, x, incomeY)
                            expensePath.cubicTo(controlX1, prevExpenseY, controlX2, expenseY, x, expenseY)

                            incomeFillPath.cubicTo(controlX1, prevIncomeY, controlX2, incomeY, x, incomeY)
                            expenseFillPath.cubicTo(controlX1, prevExpenseY, controlX2, expenseY, x, expenseY)
                        }
                    }

                    incomeFillPath.lineTo(width, height)
                    incomeFillPath.close()

                    expenseFillPath.lineTo(width, height)
                    expenseFillPath.close()

                    // Draw Gradient Fills
                    drawPath(
                        path = incomeFillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(FinovaColors.EmeraldGreen.copy(alpha = 0.25f), Color.Transparent)
                        )
                    )

                    drawPath(
                        path = expenseFillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(FinovaColors.BlueAccent.copy(alpha = 0.2f), Color.Transparent)
                        )
                    )

                    // Draw Curved Lines
                    drawPath(incomePath, color = FinovaColors.EmeraldGreen, style = Stroke(width = 3f))
                    drawPath(expensePath, color = FinovaColors.BlueAccent, style = Stroke(width = 3f))

                    // Draw Active Selection Vertical Marker
                    if (selectedIndex in dataPoints.indices) {
                        val selX = selectedIndex * stepX
                        drawLine(
                            color = FinovaColors.BlueAccent.copy(alpha = 0.5f),
                            start = Offset(selX, 0f),
                            end = Offset(selX, height),
                            strokeWidth = 2f
                        )

                        val point = dataPoints[selectedIndex]
                        val incY = height - (point.income.toFloat() / maxVal * height)
                        val expY = height - (point.expense.toFloat() / maxVal * height)

                        drawCircle(color = Color.White, radius = 6f, center = Offset(selX, incY))
                        drawCircle(color = FinovaColors.EmeraldGreen, radius = 4f, center = Offset(selX, incY))

                        drawCircle(color = Color.White, radius = 6f, center = Offset(selX, expY))
                        drawCircle(color = FinovaColors.BlueAccent, radius = 4f, center = Offset(selX, expY))
                    }
                }

                // Interactive Tooltip Box
                activePoint?.let { point ->
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 10.dp),
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp),
                        shadowElevation = 6.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(point.label, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = FinovaColors.TextPrimary)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("• Income $${point.income.toInt()}", fontSize = 10.sp, color = FinovaColors.EmeraldGreen, fontWeight = FontWeight.Bold)
                                Text("• Expense $${point.expense.toInt()}", fontSize = 10.sp, color = FinovaColors.BlueAccent, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // X-Axis Date Labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                dataPoints.forEachIndexed { i, pt ->
                    Text(
                        text = pt.label,
                        fontSize = 11.sp,
                        color = if (i == selectedIndex) FinovaColors.TextPrimary else FinovaColors.TextMuted,
                        fontWeight = if (i == selectedIndex) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
