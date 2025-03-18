package com.animotionsoftware.reactor.example.ui.screens.home.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animotionsoftware.reactor.example.domain.entities.Product

@Composable
fun ProductCard(product: Product) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp, horizontal = 16.dp)

    Card(modifier) {
        Column(modifier) {
            Text(
                product.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.W700
            )
            Text(product.description)
        }
    }
}