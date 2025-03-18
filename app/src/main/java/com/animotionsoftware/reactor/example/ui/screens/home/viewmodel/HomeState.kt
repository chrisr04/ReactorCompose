package com.animotionsoftware.reactor.example.ui.screens.home.viewmodel

import androidx.compose.runtime.Stable
import com.animotionsoftware.reactor.example.domain.entities.Product

data class HomeState(
    val products: List<Product> = emptyList<Product>(),
    val result: HomeStateResult = HomeStateResult.None,
)

@Stable
sealed interface HomeStateResult {
    data object None : HomeStateResult
    data object Loading : HomeStateResult
    data object ProductsLoaded : HomeStateResult
}