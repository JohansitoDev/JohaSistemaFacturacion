package com.example.johafcturas.viewmodel

import androidx.lifecycle.ViewModel
import com.example.johafcturas.data.InMemoryDatabase
import com.example.johafcturas.model.*
import com.example.johafcturas.ui.components.NavigationTab
import com.example.johafcturas.ui.components.QuickActionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {

    private val db = InMemoryDatabase

    // --- Authentication & Session State ---
    private val _currentUser = MutableStateFlow<User?>(InMemoryDatabase.SUPER_ADMIN_USER)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _activeTab = MutableStateFlow(NavigationTab.DASHBOARD)
    val activeTab: StateFlow<NavigationTab> = _activeTab.asStateFlow()

    // --- Reactive Database Feeds ---
    val companies: StateFlow<List<Company>> = db.companies
    val roles: StateFlow<List<UserRole>> = db.roles
    val departments: StateFlow<List<Department>> = db.departments
    val users: StateFlow<List<User>> = db.users
    val invoices: StateFlow<List<Invoice>> = db.invoices
    val products: StateFlow<List<Product>> = db.products
    val clients: StateFlow<List<Client>> = db.clients
    val accounts: StateFlow<List<AccountBalance>> = db.accounts
    val activityLogs: StateFlow<List<ActivityLog>> = db.activityLogs

    val overviewPoints: List<OverviewDataPoint> = db.OVERVIEW_DATA_POINTS
    val spendingCategories: List<SpendingCategory> = db.SPENDING_CATEGORIES

    // --- Actions ---

    fun login(username: String, pass: String): Boolean {
        val foundUser = users.value.find {
            (it.username.equals(username, ignoreCase = true) || it.email.equals(username, ignoreCase = true)) &&
                    it.passwordHash == pass && it.isActive
        }

        return if (foundUser != null) {
            _currentUser.value = foundUser
            _activeTab.value = if (foundUser.isSuperAdmin) NavigationTab.SUPER_ADMIN else NavigationTab.DASHBOARD
            true
        } else {
            false
        }
    }

    fun logout() {
        _currentUser.value = null
        _activeTab.value = NavigationTab.DASHBOARD
    }

    fun selectTab(tab: NavigationTab) {
        _activeTab.value = tab
    }

    fun handleQuickAction(action: QuickActionType) {
        when (action) {
            QuickActionType.NUEVA_FACTURA -> _activeTab.value = NavigationTab.INVOICES
            QuickActionType.AGREGAR_PRODUCTO -> _activeTab.value = NavigationTab.PRODUCTS
            QuickActionType.NUEVO_CLIENTE -> _activeTab.value = NavigationTab.CLIENTS
            QuickActionType.ROLES_PERMISOS -> _activeTab.value = NavigationTab.ORGANIZATION
        }
    }

    // --- Business Mutations (SuperAdmin & Company Admin) ---

    fun addCompany(company: Company, adminUser: User, adminRole: UserRole) {
        db.addCompany(company, adminUser, adminRole)
    }

    fun toggleCompanyStatus(companyId: String) {
        db.toggleCompanyStatus(companyId)
    }

    fun addDepartment(department: Department) {
        db.addDepartment(department)
    }

    fun toggleDepartmentStatus(deptId: String) {
        db.toggleDepartmentStatus(deptId)
    }

    fun addRole(role: UserRole) {
        db.addRole(role)
    }

    fun toggleRoleStatus(roleId: String) {
        db.toggleRoleStatus(roleId)
    }

    fun addUser(user: User) {
        db.addUser(user)
    }

    fun toggleUserStatus(userId: String) {
        db.toggleUserStatus(userId)
    }

    fun addInvoice(invoice: Invoice) {
        db.addInvoice(invoice)
    }

    fun toggleInvoiceStatus(invoiceId: String) {
        db.toggleInvoiceStatus(invoiceId)
    }

    fun addProduct(product: Product) {
        db.addProduct(product)
    }

    fun toggleProductStatus(productId: String) {
        db.toggleProductStatus(productId)
    }

    fun addClient(client: Client) {
        db.addClient(client)
    }

    fun toggleClientStatus(clientId: String) {
        db.toggleClientStatus(clientId)
    }
}
