package com.example.johafcturas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.johafcturas.model.Product
import com.example.johafcturas.ui.components.FinovaIcon
import com.example.johafcturas.ui.components.FinovaIconType
import com.example.johafcturas.ui.components.SoftDeleteActiveBadge
import com.example.johafcturas.ui.theme.FinovaColors

@Composable
fun ProductsScreen(
    products: List<Product>,
    onAddProduct: (Product) -> Unit,
    onToggleStatus: (productId: String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    val filteredProducts = products.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.sku.contains(searchQuery, ignoreCase = true)
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isDesktop = maxWidth > 750.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isDesktop) 24.dp else 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Catálogo de Productos", fontWeight = FontWeight.ExtraBold, fontSize = if (isDesktop) 24.sp else 18.sp, color = FinovaColors.TextPrimary)
                    Text("Inventario, precios y stock", fontSize = 12.sp, color = FinovaColors.TextSecondary)
                }

                Button(
                    onClick = { showAddDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        FinovaIcon(FinovaIconType.PLUS, tint = Color.White, size = 14.dp)
                        Text("+ Nuevo Producto", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            // Search Bar
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Buscar por nombre o SKU...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )
                }
            }

            // Products Grid
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = if (isDesktop) 280.dp else 220.dp),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filteredProducts) { prod ->
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(24.dp),
                        shadowElevation = 2.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(FinovaColors.SurfaceTint)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(prod.category, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = FinovaColors.TextSecondary)
                                }
                                SoftDeleteActiveBadge(prod.isActive)
                            }

                            Text(prod.name, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = FinovaColors.TextPrimary)
                            Text("SKU: ${prod.sku}", fontSize = 11.sp, color = FinovaColors.TextMuted)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Precio Venta", fontSize = 10.sp, color = FinovaColors.TextSecondary)
                                    Text("$${prod.price}", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = FinovaColors.EmeraldGreen)
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text("Stock", fontSize = 10.sp, color = FinovaColors.TextSecondary)
                                    Text(
                                        text = "${prod.stock} ud.",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (prod.stock < 5) FinovaColors.CrimsonRed else FinovaColors.TextPrimary
                                    )
                                }
                            }

                            HorizontalDivider()

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Costo: $${prod.cost}", fontSize = 10.sp, color = FinovaColors.TextSecondary)

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(FinovaColors.SurfaceTint)
                                        .clickable { onToggleStatus(prod.id) }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = if (prod.isActive) "Desactivar" else "Reactivar",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (prod.isActive) FinovaColors.CrimsonRed else FinovaColors.EmeraldGreen
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddProductModal(
            onDismiss = { showAddDialog = false },
            onConfirm = { newProd ->
                onAddProduct(newProd)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun AddProductModal(
    onDismiss: () -> Unit,
    onConfirm: (Product) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var sku by remember { mutableStateOf("SKU-${(100..999).random()}") }
    var priceText by remember { mutableStateOf("150.0") }
    var costText by remember { mutableStateOf("90.0") }
    var stockText by remember { mutableStateOf("10") }
    var category by remember { mutableStateOf("General") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Producto", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre del Producto") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = sku, onValueChange = { sku = it }, label = { Text("SKU / Código") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = priceText, onValueChange = { priceText = it }, label = { Text("Precio de Venta ($)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = costText, onValueChange = { costText = it }, label = { Text("Costo ($)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = stockText, onValueChange = { stockText = it }, label = { Text("Stock Inicial") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val p = Product(
                        id = "prod_${(1000..9999).random()}",
                        companyId = "comp_technova",
                        name = if (name.isNotBlank()) name else "Producto Genérico",
                        sku = sku,
                        price = priceText.toDoubleOrNull() ?: 100.0,
                        cost = costText.toDoubleOrNull() ?: 60.0,
                        stock = stockText.toIntOrNull() ?: 5,
                        category = category,
                        isActive = true
                    )
                    onConfirm(p)
                },
                colors = ButtonDefaults.buttonColors(containerColor = FinovaColors.DarkTealHeader)
            ) {
                Text("Guardar Producto")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
