package com.animotionsoftware.reactor.example.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object LogIn

@Serializable
data class Home(val username: String)