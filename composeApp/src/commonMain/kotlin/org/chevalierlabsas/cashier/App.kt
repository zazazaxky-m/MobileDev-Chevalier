package org.chevalierlabsas.cashier

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import org.chevalierlabsas.cashier.core.navigation.HomeDestination
import org.chevalierlabsas.cashier.core.navigation.HistoryDestination
import org.chevalierlabsas.cashier.history.presentation.HistoryScreen
import org.chevalierlabsas.cashier.history.presentation.HistoryViewModel
import org.chevalierlabsas.cashier.home.presentation.HomeScreen
import org.chevalierlabsas.cashier.home.presentation.HomeViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = HomeDestination
        ) {
            composable<HomeDestination> {
                val viewModel = koinViewModel<HomeViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                HomeScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    onNavigate = { navController.navigate(it) }
                )
            }
            composable<HistoryDestination> {
                val viewModel = koinViewModel<HistoryViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                HistoryScreen(
                    state = state,
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }
    }
}