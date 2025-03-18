package com.animotionsoftware.reactor.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.animotionsoftware.reactor.example.ui.navigation.Router
import com.animotionsoftware.reactor.example.ui.theme.ReactorComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReactorComposeTheme {
                Router()
            }
        }
    }
}