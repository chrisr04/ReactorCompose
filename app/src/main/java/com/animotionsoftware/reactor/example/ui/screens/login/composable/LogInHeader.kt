package com.animotionsoftware.reactor.example.ui.screens.login.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogInHeader() {
    Text(
        "Reactor App",
        fontSize = 28.sp,
        fontWeight = FontWeight.W700
    )
    Spacer(Modifier.height(32.dp))
}