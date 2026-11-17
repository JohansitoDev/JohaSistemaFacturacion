package com.example.johafcturas.model

enum class AppPermission(val label: String, val category: String) {
    MANAGE_COMPANIES("Gestionar Empresas", "SuperAdmin"),
    MANAGE_DEPARTMENTS("Gestionar Departamentos/Secciones", "Organización"),
    MANAGE_ROLES("Gestionar Roles y Permisos", "Organización"),
    MANAGE_STAFF("Gestionar Personal", "Organización"),
    VIEW_DASHBOARD("Ver Panel de Control", "Finanzas"),
    CREATE_INVOICES("Crear Facturas", "Facturación"),
    VIEW_INVOICES("Ver Facturas", "Facturación"),
    CANCEL_INVOICES("Anular Facturas", "Facturación"),
    MANAGE_PRODUCTS("Gestionar Productos", "Inventario"),
    VIEW_PRODUCTS("Ver Productos", "Inventario"),
    MANAGE_CLIENTES("Gestionar Clientes", "CRM"),
    VIEW_REPORTS("Ver Reportes & Análisis", "Reportes")
}

data class UserRole(
    val id: String,
    val name: String,
    val description: String,
    val companyId: String,
    val permissions: Set<AppPermission>,
    val isActive: Boolean = true, // Soft-delete flag (desactivación únicamente)
    val isSystemRole: Boolean = false
)

data class Department(
    val id: String,
    val name: String,
    val description: String,
    val companyId: String,
    val isActive: Boolean = true // Soft-delete flag
)

data class User(
    val id: String,
    val username: String,
    val passwordHash: String,
    val fullName: String,
    val email: String,
    val roleId: String,
    val departmentId: String? = null,
    val companyId: String? = null,
    val isSuperAdmin: Boolean = false,
    val isActive: Boolean = true // Soft-delete flag
)

data class Company(
    val id: String,
    val name: String,
    val rnc: String,
    val address: String,
    val phone: String,
    val email: String,
    val adminUserId: String,
    val isActive: Boolean = true, // Soft-delete flag
    val createdAt: String
)

data class Product(
    val id: String,
    val companyId: String,
    val name: String,
    val sku: String,
    val price: Double,
    val cost: Double,
    val stock: Int,
    val category: String,
    val isActive: Boolean = true // Soft-delete flag
)

data class Client(
    val id: String,
    val companyId: String,
    val name: String,
    val rnc: String,
    val email: String,
    val phone: String,
    val address: String,
    val isActive: Boolean = true // Soft-delete flag
)

enum class InvoiceStatus(val label: String) {
    PAID("Pagada"),
    PENDING("Pendiente"),
    OVERDUE("Vencida"),
    CANCELLED("Anulada")
}

data class InvoiceItem(
    val productId: String,
    val productName: String,
    val quantity: Int,
    val unitPrice: Double,
    val totalPrice: Double = quantity * unitPrice
)

data class Invoice(
    val id: String,
    val companyId: String,
    val invoiceNumber: String,
    val clientId: String,
    val clientName: String,
    val items: List<InvoiceItem>,
    val subtotal: Double,
    val tax: Double,
    val total: Double,
    val status: InvoiceStatus,
    val date: String,
    val dueDate: String,
    val createdById: String,
    val isActive: Boolean = true // Soft-delete flag
)

data class AccountBalance(
    val id: String,
    val name: String,
    val accountNumber: String,
    val balance: Double,
    val type: String,
    val iconType: String
)

data class ActivityLog(
    val id: String,
    val title: String,
    val timestamp: String,
    val status: String, // "Success", "Pending", "Warning"
    val amount: String? = null,
    val iconType: String = "user"
)

data class OverviewDataPoint(
    val label: String,
    val income: Double,
    val expense: Double
)

data class SpendingCategory(
    val category: String,
    val percentage: Float,
    val amount: Double,
    val colorHex: Long
)
