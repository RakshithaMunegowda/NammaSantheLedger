package com.example.nammasantheledger.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun NammaSantheLedgerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = Typography,
        content = content
    )
}