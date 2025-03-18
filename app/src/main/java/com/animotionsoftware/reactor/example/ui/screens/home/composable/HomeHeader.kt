package com.animotionsoftware.reactor.example.ui.screens.home.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeHeader(username: String) {
    Text(
        "Welcome, $username",
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 32.dp),
        fontSize = 22.sp,
        fontWeight = FontWeight.W700,
    )
    Spacer(Modifier.height(8.dp))
}