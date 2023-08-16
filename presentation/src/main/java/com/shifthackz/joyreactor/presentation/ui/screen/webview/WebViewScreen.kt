@file:OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("SetJavaScriptEnabled")

package com.shifthackz.joyreactor.presentation.ui.screen.webview

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreen(
    modifier: Modifier = Modifier,
    url: String,
    navigateBack: () -> Unit,
) {
    BackHandler { navigateBack() }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
//                    Text(
//                        text = url,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis,
//                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            Icons.Outlined.Close,
                            contentDescription = "Back button",
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            factory = { ctx ->
                WebView(ctx).apply {
                    clearCache(true)
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?,
                        ): Boolean {
                            request?.url?.toString()?.let(::loadUrl)
                            return false
                        }
                    }
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        blockNetworkLoads = false
                        javaScriptCanOpenWindowsAutomatically = true
                        allowContentAccess = true
                        allowFileAccess = true
                    }
                    loadUrl(url)
                }
            }
        )
    }
}
