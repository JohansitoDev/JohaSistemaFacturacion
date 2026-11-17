package com.example.johafcturas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.johafcturas.model.*
import com.example.johafcturas.ui.components.FinovaIcon
import com.example.johafcturas.ui.components.FinovaIconType
import com.example.johafcturas.ui.components.InvoiceStatusBadge
import com.example.johafcturas.ui.components.SoftDeleteActiveBadge
import com.example.johafcturas.ui.theme.FinovaColors

@Composable
fun InvoicesScreen(
    invoices: List<Invoice>,
    products: List<Product>,
    clients: List<Client>,
    onAddInvoice: (Invoice) -> Unit,
    onToggleStatus: (invoiceId: String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf<InvoiceStatus?>(null) }
    var showCreateDialog by remember { mutableStateOf(false) }

    val filteredInvoices = invoices.filter { inv ->
        val matchesSearch = inv.invoiceNumber.contains(searchQuery, ignoreCase = true) || inv.clientName.contains(searchQuery, ignoreCase = true)
        val matchesStatus = selectedFilter == null || inv.status == selectedFilter
        matchesSearch && matchesStatus
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isDesktop = maxWidth > 750.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isDesktop) 24.dp else 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- Header Row ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Gestión de Facturación", fontWeight = FontWeight.ExtraBold, fontSize = if (isDesktop) 24.sp else 18.sp, color = FinovaColors.TextPrimary)
                    Text("Comprobantes de venta y estado fiscal", fontSize = 12.sp, color = FinovaColors.TextSecondary)
                }

                Button(
                    onClick = { showCreateDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        FinovaIcon(FinovaIconType.PLUS, tint = Color.White, size = 14.dp)
                        Text("+ Nueva Factura", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            // --- Filter & Search Bar ---
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Buscar factura o cliente...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    // Filter Tabs (Scrollable on mobile)
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FilterChip(selected = selectedFilter == null, onClick = { selectedFilter = null }, label = { Text("Todas (${invoices.size})") })
                        InvoiceStatus.entries.forEach { st ->
                            FilterChip(selected = selectedFilter == st, onClick = { selectedFilter = st }, label = { Text(st.label) })
                        }
                    }
                }
            }

            // --- Invoices Table / Cards ---
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White,
                shape = RoundedCornerShape(24.dp),
                shadowElevation = 2.dp
            ) {
                if (isDesktop) {
                    // Desktop Data Table Layout
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(FinovaColors.SurfaceTint, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("No. Factura", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1.2f), color = FinovaColors.TextSecondary)
                                Text("Cliente", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1.8f), color = FinovaColors.TextSecondary)
                                Text("Fecha", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1.2f), color = FinovaColors.TextSecondary)
                                Text("ITBIS (18%)", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1.2f), color = FinovaColors.TextSecondary)
                                Text("Total", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1.2f), color = FinovaColors.TextSecondary)
                                Text("Estado", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1.2f), color = FinovaColors.TextSecondary)
                                Text("Acciones", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1.2f), color = FinovaColors.TextSecondary)
                            }
                        }

                        items(filteredInvoices) { inv ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (inv.isActive) Color.White else FinovaColors.SurfaceTint.copy(alpha = 0.5f))
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(inv.invoiceNumber, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1.2f), color = FinovaColors.TextPrimary)
                                Text(inv.clientName, fontSize = 13.sp, modifier = Modifier.weight(1.8f), color = FinovaColors.TextPrimary)
                                Text(inv.date, fontSize = 12.sp, modifier = Modifier.weight(1.2f), color = FinovaColors.TextSecondary)
                                Text("$${inv.tax}", fontSize = 12.sp, modifier = Modifier.weight(1.2f), color = FinovaColors.TextSecondary)
                                Text("$${inv.total}", fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1.2f), color = FinovaColors.TextPrimary)

                                Box(modifier = Modifier.weight(1.2f)) {
                                    InvoiceStatusBadge(inv.status)
                                }

                                Row(modifier = Modifier.weight(1.2f), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    SoftDeleteActiveBadge(inv.isActive)
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(FinovaColors.SurfaceTint)
                                            .clickable { onToggleStatus(inv.id) }
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = if (inv.isActive) "Desactivar" else "Activar",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (inv.isActive) FinovaColors.CrimsonRed else FinovaColors.EmeraldGreen
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Mobile Cards List Layout
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filteredInvoices) { inv ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (inv.isActive) FinovaColors.SurfaceTint else Color.LightGray.copy(alpha = 0.2f))
                                    .padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(inv.invoiceNumber, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = FinovaColors.DarkTealHeader)
                                    InvoiceStatusBadge(inv.status)
                                }

                                Text(inv.clientName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = FinovaColors.TextPrimary)

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("Fecha: ${inv.date}", fontSize = 11.sp, color = FinovaColors.TextSecondary)
                                        Text("ITBIS: $${inv.tax}", fontSize = 11.sp, color = FinovaColors.TextSecondary)
                                    }

                                    Text("$${inv.total}", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = FinovaColors.EmeraldGreen)
                                }

                                HorizontalDivider()

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    SoftDeleteActiveBadge(inv.isActive)

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color.White)
                                            .clickable { onToggleStatus(inv.id) }
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = if (inv.isActive) "Desactivar" else "Reactivar",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (inv.isActive) FinovaColors.CrimsonRed else FinovaColors.EmeraldGreen
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCreateDialog) {
        CreateInvoiceModal(
            products = products,
            clients = clients,
            onDismiss = { showCreateDialog = false },
            onConfirm = { newInv ->
                onAddInvoice(newInv)
                showCreateDialog = false
            }
        )
    }
}

@Composable
private fun CreateInvoiceModal(
    products: List<Product>,
    clients: List<Client>,
    onDismiss: () -> Unit,
    onConfirm: (Invoice) -> Unit
) {
    var clientName by remember { mutableStateOf(clients.firstOrNull()?.name ?: "Cliente Contado") }
    var selectedProduct by remember { mutableStateOf(products.firstOrNull()) }
    var quantityText by remember { mutableStateOf("1") }

    val qty = quantityText.toIntOrNull() ?: 1
    val unitPrice = selectedProduct?.price ?: 100.0
    val subtotal = qty * unitPrice
    val tax = subtotal * 0.18
    val total = subtotal + tax

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva Factura", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("Nombre del Cliente") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Seleccionar Producto:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    products.take(4).forEach { prod ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selectedProduct == prod) FinovaColors.EmeraldGreenLight else FinovaColors.SurfaceTint)
                                .clickable { selectedProduct = prod }
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(prod.name, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                            Text("$${prod.price}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }

                OutlinedTextField(
                    value = quantityText,
                    onValueChange = { quantityText = it },
                    label = { Text("Cantidad") },
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider()

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Subtotal: $${subtotal.toInt()}", fontSize = 12.sp)
                    Text("ITBIS (18%): $${tax.toInt()}", fontSize = 12.sp)
                    Text("Total: $${total.toInt()}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = FinovaColors.EmeraldGreen)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val randomId = (1000..9999).random()
                    val newInvoice = Invoice(
                        id = "inv_$randomId",
                        companyId = "comp_technova",
                        invoiceNumber = "FV-2024-0$randomId",
                        clientId = "cli_gen",
                        clientName = clientName,
                        items = listOf(InvoiceItem(selectedProduct?.id ?: "p1", selectedProduct?.name ?: "Producto", qty, unitPrice)),
                        subtotal = subtotal,
                        tax = tax,
                        total = total,
                        status = InvoiceStatus.PAID,
                        date = "Hoy",
                        dueDate = "30 días",
                        createdById = "usr_admin",
                        isActive = true
                    )
                    onConfirm(newInvoice)
                },
                colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader)
            ) {
                Text("Generar Factura")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
