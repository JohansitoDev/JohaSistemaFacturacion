package com.example.johafcturas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.johafcturas.model.AccountBalance
import com.example.johafcturas.model.ActivityLog
import com.example.johafcturas.model.Invoice
import com.example.johafcturas.model.OverviewDataPoint
import com.example.johafcturas.model.SpendingCategory
import com.example.johafcturas.ui.components.*
import com.example.johafcturas.ui.theme.FinovaColors

@Composable
fun DashboardScreen(
    overviewPoints: List<OverviewDataPoint>,
    spendingCategories: List<SpendingCategory>,
    accounts: List<AccountBalance>,
    invoices: List<Invoice>,
    activityLogs: List<ActivityLog>,
    onQuickAction: (QuickActionType) -> Unit,
    onViewAllInvoices: () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWide = maxWidth > 900.dp

        if (isWide) {
            // Desktop 3-Column Layout matching Finova Screenshot
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // --- Left Column (Width: ~300dp) ---
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    TotalBalanceCard()
                    MyDigitalCard()
                    AccountsCard(accounts = accounts)
                }

                // --- Middle Column (Width: ~420dp) ---
                Column(
                    modifier = Modifier.weight(1.4f),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    OverviewChartCard(dataPoints = overviewPoints)
                    RecentTransactionsCard(invoices = invoices, onViewAll = onViewAllInvoices)
                }

                // --- Right Column (Width: ~320dp) ---
                Column(
                    modifier = Modifier.weight(1.1f),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    QuickActionsGridCard(onActionClicked = onQuickAction)
                    DonutChartCard(categories = spendingCategories)
                    RecentActivityCard(logs = activityLogs)
                }
            }
        } else {
            // Mobile / Narrow Layout (Stacked Single Column)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TotalBalanceCard()
                MyDigitalCard()
                QuickActionsGridCard(onActionClicked = onQuickAction)
                OverviewChartCard(dataPoints = overviewPoints)
                AccountsCard(accounts = accounts)
                DonutChartCard(categories = spendingCategories)
                RecentTransactionsCard(invoices = invoices, onViewAll = onViewAllInvoices)
                RecentActivityCard(logs = activityLogs)
            }
        }
    }
}

@Composable
private fun TotalBalanceCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total Balance",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = FinovaColors.TextSecondary
                )
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(FinovaColors.EmeraldGreenLight),
                    contentAlignment = Alignment.Center
                ) {
                    FinovaIcon(type = FinovaIconType.BANK, tint = FinovaColors.EmeraldGreen, size = 18.dp)
                }
            }

            Text(
                text = "$32,000.00",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                color = FinovaColors.TextPrimary
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("↑ 12.5%", color = FinovaColors.EmeraldGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text("from last month", color = FinovaColors.TextMuted, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun MyDigitalCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = FinovaColors.DarkCardBg,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("My Card )))", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(FinovaColors.CrimsonRed),
                    contentAlignment = Alignment.Center
                ) {
                    Text("MC", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            Text(
                text = "•••• •••• 1805",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                letterSpacing = 2.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Exp 10/30", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable { }
                ) {
                    Text("View card", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Text("→", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun AccountsCard(accounts: List<AccountBalance>) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Accounts", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = FinovaColors.TextPrimary)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(FinovaColors.SurfaceTint)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("+ Add", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = FinovaColors.TextPrimary)
                }
            }

            accounts.forEach { acc ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        val iconType = when (acc.iconType) {
                            "piggy" -> FinovaIconType.PIGGY
                            "briefcase" -> FinovaIconType.BRIEFCASE
                            else -> FinovaIconType.BANK
                        }
                        val iconBg = when (acc.iconType) {
                            "piggy" -> FinovaColors.BlueAccent.copy(alpha = 0.15f)
                            "briefcase" -> FinovaColors.PurpleAccent.copy(alpha = 0.15f)
                            else -> FinovaColors.EmeraldGreenLight
                        }
                        val iconColor = when (acc.iconType) {
                            "piggy" -> FinovaColors.BlueAccent
                            "briefcase" -> FinovaColors.PurpleAccent
                            else -> FinovaColors.EmeraldGreen
                        }

                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(iconBg),
                            contentAlignment = Alignment.Center
                        ) {
                            FinovaIcon(type = iconType, tint = iconColor, size = 18.dp)
                        }

                        Column {
                            Text(acc.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FinovaColors.TextPrimary)
                            Text(acc.accountNumber, fontSize = 11.sp, color = FinovaColors.TextSecondary)
                        }
                    }

                    Text(
                        text = "$${acc.balance}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = FinovaColors.TextPrimary
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("View all accounts", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = FinovaColors.TextPrimary)
                Text(">", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = FinovaColors.TextPrimary)
            }
        }
    }
}

@Composable
private fun RecentTransactionsCard(
    invoices: List<Invoice>,
    onViewAll: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recent Transactions", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = FinovaColors.TextPrimary)
                Text(
                    text = "View All",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = FinovaColors.BlueAccent,
                    modifier = Modifier.clickable { onViewAll() }
                )
            }

            invoices.take(5).forEach { inv ->
                val isIncome = inv.total > 500.0 // Visual differentiator matching design
                val amtPrefix = if (isIncome) "+ $" else "- $"
                val amtColor = if (isIncome) FinovaColors.EmeraldGreen else FinovaColors.CrimsonRed

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isIncome) FinovaColors.EmeraldGreenLight else FinovaColors.CrimsonRedLight),
                            contentAlignment = Alignment.Center
                        ) {
                            FinovaIcon(
                                type = if (isIncome) FinovaIconType.INVOICES else FinovaIconType.BILL,
                                tint = if (isIncome) FinovaColors.EmeraldGreen else FinovaColors.CrimsonRed,
                                size = 18.dp
                            )
                        }

                        Column {
                            Text(inv.clientName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FinovaColors.TextPrimary)
                            Text(inv.date, fontSize = 11.sp, color = FinovaColors.TextSecondary)
                        }
                    }

                    Text(
                        text = "$amtPrefix${inv.total}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = amtColor
                    )
                }
            }
        }
    }
}

@Composable
private fun RecentActivityCard(logs: List<ActivityLog>) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Recent Activity", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = FinovaColors.TextPrimary)
                Text("View All", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = FinovaColors.BlueAccent)
            }

            logs.take(5).forEach { log ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(FinovaColors.SurfaceTint),
                            contentAlignment = Alignment.Center
                        ) {
                            FinovaIcon(
                                type = when (log.iconType) {
                                    "send" -> FinovaIconType.SEND
                                    "bill" -> FinovaIconType.BILL
                                    "card" -> FinovaIconType.CARD
                                    else -> FinovaIconType.USER
                                },
                                tint = FinovaColors.TextPrimary,
                                size = 16.dp
                            )
                        }

                        Column {
                            Text(log.title, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = FinovaColors.TextPrimary)
                            Text(log.timestamp, fontSize = 10.sp, color = FinovaColors.TextSecondary)
                        }
                    }

                    if (log.amount != null) {
                        Text(
                            text = log.amount,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = if (log.amount.startsWith("+")) FinovaColors.EmeraldGreen else FinovaColors.CrimsonRed
                        )
                    } else {
                        Text(
                            text = log.status,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = FinovaColors.EmeraldGreen
                        )
                    }
                }
            }
        }
    }
}
