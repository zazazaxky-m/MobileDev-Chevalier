package org.chevalierlabsas.cashier

import androidx.compose.ui.window.ComposeUIViewController
import org.chevalierlabsas.cashier.core.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }