package com.animotionsoftware.reactor.example.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.animotionsoftware.lib.reactor.composable.ReactorEffect
import com.animotionsoftware.lib.reactor.composable.collectAsReactorStateWithLifecycle
import com.animotionsoftware.lib.reactor.composable.viewModelPreview
import com.animotionsoftware.lib.reactor.util.EffectMode
import com.animotionsoftware.lib.reactor.util.ReactorFlow
import com.animotionsoftware.reactor.example.domain.entities.Product
import com.animotionsoftware.reactor.example.ui.screens.home.composable.HomeHeader
import com.animotionsoftware.reactor.example.ui.screens.home.composable.ProductCard
import com.animotionsoftware.reactor.example.ui.screens.home.viewmodel.HomeEvent
import com.animotionsoftware.reactor.example.ui.screens.home.viewmodel.HomeEvent.*
import com.animotionsoftware.reactor.example.ui.screens.home.viewmodel.HomeState
import com.animotionsoftware.reactor.example.ui.screens.home.viewmodel.HomeStateResult
import com.animotionsoftware.reactor.example.ui.theme.ReactorComposeTheme

@Composable
fun HomeScreen(
    flow: ReactorFlow<HomeState>,
    username: String,
    onEvent: (HomeEvent) -> Unit,
) {
    val state by flow.collectAsReactorStateWithLifecycle()
    val isLoading = state.result is HomeStateResult.Loading

    ReactorEffect(
        flow,
        mode = EffectMode.Startable
    ) {
        onStart { onEvent(LoadProductsEvent) }
    }

    Scaffold { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            if (isLoading) {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn {
                    item { HomeHeader(username) }
                    items(state.products, key = { it.name }) {
                        ProductCard(it)
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val viewModel = viewModelPreview<HomeEvent, HomeState>(
        HomeState(
            products = listOf(
                Product(
                    id = 1,
                    name = "Preview Product",
                    description = "This is the preview description"
                )
            )
        )
    )
    ReactorComposeTheme {
        HomeScreen(
            flow = viewModel.flow,
            username = "John",
            onEvent = {},
        )
    }
}