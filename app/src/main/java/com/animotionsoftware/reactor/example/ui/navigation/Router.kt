package com.animotionsoftware.reactor.example.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.animotionsoftware.reactor.example.ui.screens.home.HomeScreen
import com.animotionsoftware.reactor.example.ui.screens.home.viewmodel.HomeViewModel
import com.animotionsoftware.reactor.example.ui.screens.login.LogInScreen
import com.animotionsoftware.reactor.example.ui.screens.login.viewmodel.LogInViewModel

@Composable
fun Router() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = LogIn) {
        composable<LogIn> {
            val viewModel = viewModel<LogInViewModel>()
            LogInScreen(
                flow = viewModel.flow,
                onEvent = viewModel::onEvent,
                navigateToHome = { navController.navigate(Home(it)) },
            )
        }
        composable<Home> { backStackEntry ->
            val viewModel = viewModel<HomeViewModel>()
            val route = backStackEntry.toRoute<Home>()
            HomeScreen(
                flow = viewModel.flow,
                username = route.username,
                onEvent = viewModel::onEvent,
            )
        }
    }
}