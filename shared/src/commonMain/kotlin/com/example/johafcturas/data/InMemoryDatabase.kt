package com.example.johafcturas.data

import com.example.johafcturas.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object InMemoryDatabase {

    // --- Seed Data Constants ---
    val SUPER_ADMIN_USER = User(
        id = "usr_superadmin",
        username = "superadmin",
        passwordHash = "admin123",
        fullName = "Super Admin Central",
        email = "superadmin@johafcturas.com",
        roleId = "role_superadmin",
        companyId = null,
        isSuperAdmin = true,
        isActive = true
    )

    private val DEFAULT_SUPERADMIN_ROLE = UserRole(
        id = "role_superadmin",
        name = "Super Admin",
        description = "Acceso total del sistema multi-empresa",
        companyId = "global",
        permissions = AppPermission.entries.toSet(),
        isActive = true,
        isSystemRole = true
    )

    private val DEFAULT_COMPANY = Company(
        id = "comp_technova",
        name = "TechNova Solutions S.R.L.",
        rnc = "130-99482-1",
        address = "Av. Winston Churchill #105, Santo Domingo",
        phone = "+1 (809) 555-0199",
        email = "contacto@technova.com",
        adminUserId = "usr_admin_tech",
        isActive = true,
        createdAt = "2024-01-15"
    )

    private val DEFAULT_COMPANY_ADMIN = User(
        id = "usr_admin_tech",
        username = "admin_tech",
        passwordHash = "pass123",
        fullName = "Alberto López García",
        email = "alberto@technova.com",
        roleId = "role_admin_tech",
        departmentId = "dept_admin",
        companyId = "comp_technova",
        isSuperAdmin = false,
        isActive = true
    )

    private val DEFAULT_ADMIN_ROLE = UserRole(
        id = "role_admin_tech",
        name = "Administrador de Empresa",
        description = "Administración total dentro de la empresa",
        companyId = "comp_technova",
        permissions = AppPermission.entries.filter { it != AppPermission.MANAGE_COMPANIES }.toSet(),
        isActive = true,
        isSystemRole = true
    )

    private val DEFAULT_SALES_ROLE = UserRole(
        id = "role_sales_tech",
        name = "Vendedor Ejecutivo",
        description = "Permisos de creación y consulta de facturas y clientes",
        companyId = "comp_technova",
        permissions = setOf(
            AppPermission.VIEW_DASHBOARD,
            AppPermission.CREATE_INVOICES,
            AppPermission.VIEW_INVOICES,
            AppPermission.VIEW_PRODUCTS,
            AppPermission.MANAGE_CLIENTES
        ),
        isActive = true,
        isSystemRole = false
    )

    private val DEFAULT_DEPARTMENTS = listOf(
        Department("dept_admin", "Administración Central", "Dirección ejecutiva y finanzas", "comp_technova", true),
        Department("dept_ventas", "Ventas & Comercial", "Atención a clientes y facturación rápida", "comp_technova", true),
        Department("dept_inventario", "Almacén y Logística", "Gestión de stock de mercancías", "comp_technova", true)
    )

    private val DEFAULT_STAFF = listOf(
        DEFAULT_COMPANY_ADMIN,
        User(
            id = "usr_sales_1",
            username = "maria_v",
            passwordHash = "pass123",
            fullName = "María Rodríguez",
            email = "maria@technova.com",
            roleId = "role_sales_tech",
            departmentId = "dept_ventas",
            companyId = "comp_technova",
            isSuperAdmin = false,
            isActive = true
        ),
        User(
            id = "usr_sales_2",
            username = "carlos_m",
            passwordHash = "pass123",
            fullName = "Carlos Mendoza",
            email = "carlos@technova.com",
            roleId = "role_sales_tech",
            departmentId = "dept_inventario",
            companyId = "comp_technova",
            isSuperAdmin = false,
            isActive = false // Soft-deleted (Desactivado) for testing UI
        )
    )

    private val DEFAULT_ACCOUNTS = listOf(
        AccountBalance("acc_1", "Main Account", "•••• 1805", 12450.00, "Corriente", "bank"),
        AccountBalance("acc_2", "Savings Account", "•••• 2456", 8750.00, "Ahorro", "piggy"),
        AccountBalance("acc_3", "Business Account", "•••• 7689", 10800.00, "Empresarial", "briefcase")
    )

    private val DEFAULT_INVOICES = listOf(
        Invoice(
            id = "inv_101",
            companyId = "comp_technova",
            invoiceNumber = "FV-2024-001",
            clientId = "cli_1",
            clientName = "Salary from TechNova",
            items = listOf(InvoiceItem("p1", "Consultoría Tecnológica", 1, 5250.00)),
            subtotal = 4449.15,
            tax = 800.85,
            total = 5250.00,
            status = InvoiceStatus.PAID,
            date = "28 Apr, 2024",
            dueDate = "28 May, 2024",
            createdById = "usr_admin_tech",
            isActive = true
        ),
        Invoice(
            id = "inv_102",
            companyId = "comp_technova",
            invoiceNumber = "FV-2024-002",
            clientId = "cli_2",
            clientName = "Amazon Purchase",
            items = listOf(InvoiceItem("p2", "Equipos de Oficina & Servidores", 1, 120.50)),
            subtotal = 102.12,
            tax = 18.38,
            total = 120.50,
            status = InvoiceStatus.PAID,
            date = "27 Apr, 2024",
            dueDate = "27 May, 2024",
            createdById = "usr_sales_1",
            isActive = true
        ),
        Invoice(
            id = "inv_103",
            companyId = "comp_technova",
            invoiceNumber = "FV-2024-003",
            clientId = "cli_3",
            clientName = "Electricity Bill",
            items = listOf(InvoiceItem("p3", "Servicio Eléctrico Industrial", 1, 80.00)),
            subtotal = 67.80,
            tax = 12.20,
            total = 80.00,
            status = InvoiceStatus.PENDING,
            date = "26 Apr, 2024",
            dueDate = "10 May, 2024",
            createdById = "usr_admin_tech",
            isActive = true
        ),
        Invoice(
            id = "inv_104",
            companyId = "comp_technova",
            invoiceNumber = "FV-2024-004",
            clientId = "cli_4",
            clientName = "Spotify Subscription",
            items = listOf(InvoiceItem("p4", "Licencia Software & Streaming", 1, 9.99)),
            subtotal = 8.47,
            tax = 1.52,
            total = 9.99,
            status = InvoiceStatus.PAID,
            date = "25 Apr, 2024",
            dueDate = "25 May, 2024",
            createdById = "usr_sales_1",
            isActive = true
        ),
        Invoice(
            id = "inv_105",
            companyId = "comp_technova",
            invoiceNumber = "FV-2024-005",
            clientId = "cli_5",
            clientName = "Flight Ticket",
            items = listOf(InvoiceItem("p5", "Vuelo Conferencia Tech", 1, 320.00)),
            subtotal = 271.19,
            tax = 48.81,
            total = 320.00,
            status = InvoiceStatus.OVERDUE,
            date = "24 Apr, 2024",
            dueDate = "01 May, 2024",
            createdById = "usr_admin_tech",
            isActive = true
        )
    )

    private val DEFAULT_PRODUCTS = listOf(
        Product("prod_1", "comp_technova", "Laptop Dell XPS 15", "SKU-DELL-15", 1450.00, 1100.00, 12, "Laptops", true),
        Product("prod_2", "comp_technova", "Monitor LG UltraWide 34\"", "SKU-LG-34", 499.99, 350.00, 8, "Monitores", true),
        Product("prod_3", "comp_technova", "Teclado Mecánico RGB", "SKU-KEY-RGB", 89.99, 50.00, 25, "Accesorios", true),
        Product("prod_4", "comp_technova", "Mouse Ergonómico Inalámbrico", "SKU-MSE-ERG", 45.00, 25.00, 3, "Accesorios", true),
        Product("prod_5", "comp_technova", "Servidor Rack 1U PowerEdge", "SKU-SRV-1U", 3200.00, 2400.00, 2, "Servidores", true)
    )

    private val DEFAULT_CLIENTS = listOf(
        Client("cli_1", "comp_technova", "TechNova Partner Inc.", "101-99881-2", "partner@technova.com", "+1 (809) 555-1111", "Santo Domingo", true),
        Client("cli_2", "comp_technova", "Corporación Global S.A.", "130-44551-9", "compras@globalcorp.com", "+1 (809) 555-2222", "Santiago", true),
        Client("cli_3", "comp_technova", "Importadora del Caribe", "102-33441-5", "info@impcaribe.com", "+1 (809) 555-3333", "Punta Cana", true)
    )

    private val DEFAULT_ACTIVITY_LOGS = listOf(
        ActivityLog("act_1", "Login from new device", "28 Apr, 2024 at 10:30 AM", "Success", iconType = "user"),
        ActivityLog("act_2", "Money sent to John Doe", "27 Apr, 2024 at 04:15 PM", "Success", amount = "- ₹250.00", iconType = "send"),
        ActivityLog("act_3", "Bill payment Electricity", "26 Apr, 2024 at 11:20 AM", "Success", amount = "- ₹80.00", iconType = "bill"),
        ActivityLog("act_4", "Funds added via Credit Card", "25 Apr, 2024 at 09:10 AM", "Success", amount = "+ ₹500.00", iconType = "card"),
        ActivityLog("act_5", "Profile information updated", "24 Apr, 2024 at 03:45 PM", "Success", iconType = "user")
    )

    val OVERVIEW_DATA_POINTS = listOf(
        OverviewDataPoint("1 Apr", 4200.0, 1800.0),
        OverviewDataPoint("7 Apr", 8500.0, 3200.0),
        OverviewDataPoint("14 Apr", 12100.0, 4900.0),
        OverviewDataPoint("18 Apr", 15230.0, 5620.0),
        OverviewDataPoint("21 Apr", 16800.0, 6100.0),
        OverviewDataPoint("28 Apr", 18200.0, 6900.0),
        OverviewDataPoint("30 Apr", 18750.0, 7250.0)
    )

    val SPENDING_CATEGORIES = listOf(
        SpendingCategory("Shopping", 35f, 2537.50, 0xFF22C55E),
        SpendingCategory("Transport", 25f, 1812.50, 0xFF3B82F6),
        SpendingCategory("Bills", 20f, 1450.00, 0xFFEAB308),
        SpendingCategory("Food", 12f, 870.00, 0xFFA855F7),
        SpendingCategory("Others", 8f, 580.00, 0xFFEC4899)
    )

    // --- Reactive State Stores ---
    private val _companies = MutableStateFlow(listOf(DEFAULT_COMPANY))
    val companies: StateFlow<List<Company>> = _companies.asStateFlow()

    private val _roles = MutableStateFlow(listOf(DEFAULT_SUPERADMIN_ROLE, DEFAULT_ADMIN_ROLE, DEFAULT_SALES_ROLE))
    val roles: StateFlow<List<UserRole>> = _roles.asStateFlow()

    private val _departments = MutableStateFlow(DEFAULT_DEPARTMENTS)
    val departments: StateFlow<List<Department>> = _departments.asStateFlow()

    private val _users = MutableStateFlow(listOf(SUPER_ADMIN_USER) + DEFAULT_STAFF)
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _invoices = MutableStateFlow(DEFAULT_INVOICES)
    val invoices: StateFlow<List<Invoice>> = _invoices.asStateFlow()

    private val _products = MutableStateFlow(DEFAULT_PRODUCTS)
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _clients = MutableStateFlow(DEFAULT_CLIENTS)
    val clients: StateFlow<List<Client>> = _clients.asStateFlow()

    private val _accounts = MutableStateFlow(DEFAULT_ACCOUNTS)
    val accounts: StateFlow<List<AccountBalance>> = _accounts.asStateFlow()

    private val _activityLogs = MutableStateFlow(DEFAULT_ACTIVITY_LOGS)
    val activityLogs: StateFlow<List<ActivityLog>> = _activityLogs.asStateFlow()

    // --- State Mutations (with Soft-Delete Rules) ---

    // 1. Company Operations (SuperAdmin)
    fun addCompany(company: Company, adminUser: User, adminRole: UserRole) {
        _companies.update { it + company }
        _roles.update { it + adminRole }
        _users.update { it + adminUser }
        addLog("Nueva empresa registrada: ${company.name}")
    }

    fun toggleCompanyStatus(companyId: String) {
        _companies.update { list ->
            list.map { if (it.id == companyId) it.copy(isActive = !it.isActive) else it }
        }
        addLog("Estado de empresa actualizado (id: $companyId)")
    }

    // 2. Department Operations
    fun addDepartment(department: Department) {
        _departments.update { it + department }
        addLog("Nuevo departamento: ${department.name}")
    }

    fun toggleDepartmentStatus(deptId: String) {
        _departments.update { list ->
            list.map { if (it.id == deptId) it.copy(isActive = !it.isActive) else it }
        }
    }

    // 3. Role Operations
    fun addRole(role: UserRole) {
        _roles.update { it + role }
        addLog("Nuevo rol creado: ${role.name}")
    }

    fun updateRole(updatedRole: UserRole) {
        _roles.update { list ->
            list.map { if (it.id == updatedRole.id) updatedRole else it }
        }
    }

    fun toggleRoleStatus(roleId: String) {
        _roles.update { list ->
            list.map { if (it.id == roleId) it.copy(isActive = !it.isActive) else it }
        }
        addLog("Estado de rol modificado (id: $roleId)")
    }

    // 4. User / Staff Operations
    fun addUser(user: User) {
        _users.update { it + user }
        addLog("Nuevo usuario registrado: ${user.fullName}")
    }

    fun toggleUserStatus(userId: String) {
        _users.update { list ->
            list.map { if (it.id == userId) it.copy(isActive = !it.isActive) else it }
        }
        addLog("Estado de usuario modificado (id: $userId)")
    }

    // 5. Invoicing Operations
    fun addInvoice(invoice: Invoice) {
        _invoices.update { listOf(invoice) + it }
        addLog("Factura creada #${invoice.invoiceNumber} ($${invoice.total})")
    }

    fun toggleInvoiceStatus(invoiceId: String) {
        _invoices.update { list ->
            list.map { if (it.id == invoiceId) it.copy(isActive = !it.isActive) else it }
        }
    }

    // 6. Product Operations
    fun addProduct(product: Product) {
        _products.update { it + product }
        addLog("Producto agregado: ${product.name}")
    }

    fun toggleProductStatus(productId: String) {
        _products.update { list ->
            list.map { if (it.id == productId) it.copy(isActive = !it.isActive) else it }
        }
    }

    // 7. Client Operations
    fun addClient(client: Client) {
        _clients.update { it + client }
        addLog("Cliente registrado: ${client.name}")
    }

    fun toggleClientStatus(clientId: String) {
        _clients.update { list ->
            list.map { if (it.id == clientId) it.copy(isActive = !it.isActive) else it }
        }
    }

    private var logCounter = 100
    private fun addLog(title: String) {
        logCounter++
        val newLog = ActivityLog(
            id = "log_$logCounter",
            title = title,
            timestamp = "Justo ahora",
            status = "Success",
            iconType = "user"
        )
        _activityLogs.update { listOf(newLog) + it }
    }
}
