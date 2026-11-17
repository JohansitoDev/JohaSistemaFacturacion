package com.example.johafcturas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.johafcturas.ui.components.FinovaBottomBar
import com.example.johafcturas.ui.components.FinovaTopBar
import com.example.johafcturas.ui.components.NavigationTab
import com.example.johafcturas.ui.screens.*
import com.example.johafcturas.ui.theme.FinovaColors
import com.example.johafcturas.ui.theme.FinovaTheme
import com.example.johafcturas.viewmodel.AppViewModel

@Composable
@Preview
fun App() {
    FinovaTheme {
        val viewModel: AppViewModel = viewModel { AppViewModel() }

        val currentUser by viewModel.currentUser.collectAsState()
        val activeTab by viewModel.activeTab.collectAsState()

        val companies by viewModel.companies.collectAsState()
        val roles by viewModel.roles.collectAsState()
        val departments by viewModel.departments.collectAsState()
        val users by viewModel.users.collectAsState()
        val invoices by viewModel.invoices.collectAsState()
        val products by viewModel.products.collectAsState()
        val clients by viewModel.clients.collectAsState()
        val accounts by viewModel.accounts.collectAsState()
        val activityLogs by viewModel.activityLogs.collectAsState()

        if (currentUser == null) {
            LoginScreen(
                onLoginAttempt = { username, pass -> viewModel.login(username, pass) }
            )
        } else {
            Scaffold(
                topBar = {
                    FinovaTopBar(
                        activeTab = activeTab,
                        onTabSelected = { viewModel.selectTab(it) },
                        currentUser = currentUser,
                        onLogout = { viewModel.logout() }
                    )
                },
                bottomBar = {
                    FinovaBottomBar()
                },
                containerColor = FinovaColors.PistachioBg
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(FinovaColors.PistachioBg)
                ) {
                    when (activeTab) {
                        NavigationTab.DASHBOARD -> {
                            DashboardScreen(
                                overviewPoints = viewModel.overviewPoints,
                                spendingCategories = viewModel.spendingCategories,
                                accounts = accounts,
                                invoices = invoices,
                                activityLogs = activityLogs,
                                onQuickAction = { viewModel.handleQuickAction(it) },
                                onViewAllInvoices = { viewModel.selectTab(NavigationTab.INVOICES) }
                            )
                        }
                        NavigationTab.INVOICES -> {
                            InvoicesScreen(
                                invoices = invoices,
                                products = products,
                                clients = clients,
                                onAddInvoice = { viewModel.addInvoice(it) },
                                onToggleStatus = { viewModel.toggleInvoiceStatus(it) }
                            )
                        }
                        NavigationTab.PRODUCTS -> {
                            ProductsScreen(
                                products = products,
                                onAddProduct = { viewModel.addProduct(it) },
                                onToggleStatus = { viewModel.toggleProductStatus(it) }
                            )
                        }
                        NavigationTab.CLIENTS -> {
                            InvoicesScreen(
                                invoices = invoices,
                                products = products,
                                clients = clients,
                                onAddInvoice = { viewModel.addInvoice(it) },
                                onToggleStatus = { viewModel.toggleInvoiceStatus(it) }
                            )
                        }
                        NavigationTab.ORGANIZATION -> {
                            OrganizationScreen(
                                departments = departments,
                                roles = roles,
                                users = users,
                                onAddDepartment = { viewModel.addDepartment(it) },
                                onToggleDepartmentStatus = { viewModel.toggleDepartmentStatus(it) },
                                onAddRole = { viewModel.addRole(it) },
                                onToggleRoleStatus = { viewModel.toggleRoleStatus(it) },
                                onAddUser = { viewModel.addUser(it) },
                                onToggleUserStatus = { viewModel.toggleUserStatus(it) }
                            )
                        }
                        NavigationTab.SUPER_ADMIN -> {
                            SuperAdminScreen(
                                companies = companies,
                                users = users,
                                onAddCompany = { c, u, r -> viewModel.addCompany(c, u, r) },
                                onToggleCompanyStatus = { viewModel.toggleCompanyStatus(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}
