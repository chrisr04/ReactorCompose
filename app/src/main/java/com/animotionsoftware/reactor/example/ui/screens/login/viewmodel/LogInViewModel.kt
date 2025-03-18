package com.animotionsoftware.reactor.example.ui.screens.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.animotionsoftware.lib.reactor.viewmodel.ReactorViewModel
import com.animotionsoftware.reactor.example.domain.entities.User
import com.animotionsoftware.reactor.example.ui.screens.login.viewmodel.LogInEvent.*
import com.animotionsoftware.reactor.example.ui.screens.login.viewmodel.LogInStateResult.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LogInViewModel() : ReactorViewModel<LogInEvent, LogInState>(LogInState()) {

    init {
        on<ChangeEmailEvent>(::onChangeEmailEvent)
        on<ChangePasswordEvent>(::onChangePasswordEvent)
        on<ValidateUserEvent>(::onValidateUserEvent)
    }

    private fun onChangeEmailEvent(event: ChangeEmailEvent) {
        emit(state.copy(email = event.email, result = Changed))
    }

    private fun onChangePasswordEvent(event: ChangePasswordEvent) {
        emit(state.copy(password = event.password, result = Changed))
    }

    private fun onValidateUserEvent() {
        viewModelScope.launch {
            emit(state.copy(result = Checking))
            delay(1000L)
            val user = User("John")
            emit(state.copy(result = ValidUser(user.name)))
        }
    }
}