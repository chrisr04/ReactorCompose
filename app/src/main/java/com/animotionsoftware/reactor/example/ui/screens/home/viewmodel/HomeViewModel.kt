package com.animotionsoftware.reactor.example.ui.screens.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.animotionsoftware.lib.reactor.viewmodel.ReactorViewModel
import com.animotionsoftware.reactor.example.domain.entities.Product
import com.animotionsoftware.reactor.example.ui.screens.home.viewmodel.HomeEvent.*
import com.animotionsoftware.reactor.example.ui.screens.home.viewmodel.HomeStateResult.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ReactorViewModel<HomeEvent, HomeState>(HomeState()) {

    init {
        on<LoadProductsEvent>(::onLoadProductsEvent)
    }

    private fun onLoadProductsEvent() {
        viewModelScope.launch {
            emit(state.copy(result = Loading))
            delay(1000L)
            val products = buildProducts()
            emit(state.copy(products = products.toList(), result = ProductsLoaded))
        }
    }

    private fun buildProducts(): List<Product> {
        val products = mutableListOf<Product>()
        repeat(20) {
            products.add(
                Product(
                    id = it,
                    name = "Product ${it + 1}",
                    description = "This is the product #${it + 1}"
                )
            )
        }
        return products.toList()
    }
}