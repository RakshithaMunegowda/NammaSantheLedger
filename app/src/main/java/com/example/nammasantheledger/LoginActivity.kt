package com.example.nammasantheledger

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nammasantheledger.ui.LoginScreen
import com.example.nammasantheledger.ui.theme.NammaSantheLedgerTheme

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NammaSantheLedgerTheme {
                LoginScreen(
                    isRegistering = false,
                    onModeChange = {},
                    onLogin = { _, _ ->
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    },
                    onRegister = { _ -> },
                    onForgotPassword = { _, _ -> }
                )
            }
        }
    }
}