package com.animotionsoftware.reactor.example.ui.screens.login.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.animotionsoftware.reactor.example.ui.common.composable.OutlinedInput

@Composable
fun LogInPasswordField(
    password: String,
    readOnly: Boolean,
    onChangePassword: (String) -> Unit,
) {
    OutlinedInput(
        value = password,
        placeholder = "Enter your password",
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = PasswordVisualTransformation(),
        onValueChange = onChangePassword
    )
    Spacer(modifier = Modifier.height(16.dp))
}