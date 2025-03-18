package com.animotionsoftware.reactor.example.ui.screens.home.viewmodel

import com.animotionsoftware.lib.reactor.viewmodel.ReactorEvent

sealed interface HomeEvent : ReactorEvent {
    data object LoadProductsEvent : HomeEvent
}