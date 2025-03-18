package com.animotionsoftware.reactor.example.ui.screens.login.viewmodel

import android.util.Patterns
import androidx.compose.runtime.Stable

data class LogInState(
    val email: String = "",
    val password: String = "",
    val result: LogInStateResult = LogInStateResult.None,
) {
    fun isFormValid() = Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6
}

@Stable
sealed interface LogInStateResult {
    data object None : LogInStateResult
    data object Changed : LogInStateResult
    data object Checking : LogInStateResult
    data class ValidUser(val username: String) : LogInStateResult
}