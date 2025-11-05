package id.co.hasilkarya.smarthome

import androidx.compose.ui.window.ComposeUIViewController
import id.co.hasilkarya.smarthome.core.App
import id.co.hasilkarya.smarthome.core.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }