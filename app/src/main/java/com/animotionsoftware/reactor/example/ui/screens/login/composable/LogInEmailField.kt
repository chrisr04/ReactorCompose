package com.animotionsoftware.reactor.example.ui.screens.login.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.animotionsoftware.reactor.example.ui.common.composable.OutlinedInput

@Composable
fun LogInEmailField(
    email: String,
    readOnly: Boolean,
    onChangeEmail: (String) -> Unit,
) {
    OutlinedInput(
        value = email,
        placeholder = "Enter your email",
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
        onValueChange = onChangeEmail
    )
    Spacer(modifier = Modifier.height(16.dp))
}