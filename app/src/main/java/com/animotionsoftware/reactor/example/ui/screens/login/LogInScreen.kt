package com.animotionsoftware.reactor.example.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.animotionsoftware.lib.reactor.composable.ReactorEffect
import com.animotionsoftware.lib.reactor.composable.collectAsReactorStateWithLifecycle
import com.animotionsoftware.lib.reactor.composable.viewModelPreview
import com.animotionsoftware.lib.reactor.util.ReactorFlow
import com.animotionsoftware.reactor.example.ui.screens.login.composable.LogInButton
import com.animotionsoftware.reactor.example.ui.screens.login.composable.LogInEmailField
import com.animotionsoftware.reactor.example.ui.screens.login.composable.LogInHeader
import com.animotionsoftware.reactor.example.ui.screens.login.composable.LogInPasswordField
import com.animotionsoftware.reactor.example.ui.screens.login.viewmodel.LogInEvent
import com.animotionsoftware.reactor.example.ui.screens.login.viewmodel.LogInEvent.*
import com.animotionsoftware.reactor.example.ui.screens.login.viewmodel.LogInState
import com.animotionsoftware.reactor.example.ui.screens.login.viewmodel.LogInStateResult.*
import com.animotionsoftware.reactor.example.ui.theme.ReactorComposeTheme

@Composable
fun LogInScreen(
    flow: ReactorFlow<LogInState>,
    onEvent: (LogInEvent) -> Unit,
    navigateToHome: (String) -> Unit,
) {
    val state by flow.collectAsReactorStateWithLifecycle()
    val isLoading = state.result is Checking

    ReactorEffect(
        flow,
        reactWhen = { it.result is ValidUser }
    ) {
        when (it.result) {
            is ValidUser -> {
                navigateToHome(it.result.username)
            }

            else -> Unit
        }
    }

    Scaffold(Modifier.padding(horizontal = 16.dp)) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogInHeader()
            LogInEmailField(
                email = state.email,
                readOnly = isLoading,
                onChangeEmail = { onEvent(ChangeEmailEvent(it)) }
            )
            LogInPasswordField(
                password = state.password,
                readOnly = isLoading,
                onChangePassword = { onEvent(ChangePasswordEvent(it)) }
            )
            LogInButton(
                isEnabled = state.isFormValid(),
                isLoading = isLoading,
                onClick = { onEvent(ValidateUserEvent) }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LogInScreenPreview() {
    val viewModel = viewModelPreview<LogInEvent, LogInState>(
        LogInState(
            email = "preview@email.com",
            password = "1234567"
        )
    )
    ReactorComposeTheme {
        LogInScreen(
            flow = viewModel.flow,
            onEvent = {},
            navigateToHome = {}
        )
    }
}