package ru.g000sha256.lightcycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun VisibilityHandler(visibilityCallback: VisibilityCallback) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> visibilityCallback.onStart()
                Lifecycle.Event.ON_STOP -> visibilityCallback.onStop()
                else -> Unit
            }
        }
        lifecycle.addObserver(lifecycleObserver)
        return@DisposableEffect onDispose {
            lifecycle.removeObserver(lifecycleObserver)
            visibilityCallback.onStop()
        }
    }
}