package com.animotionsoftware.reactor.example.ui.screens.login.viewmodel

import com.animotionsoftware.lib.reactor.viewmodel.ReactorEvent

sealed interface LogInEvent : ReactorEvent {
    data class ChangeEmailEvent(val email: String) : LogInEvent
    data class ChangePasswordEvent(val password: String) : LogInEvent
    data object ValidateUserEvent : LogInEvent
}