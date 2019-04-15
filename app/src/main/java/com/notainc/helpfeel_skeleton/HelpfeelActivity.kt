package com.notainc.helpfeel_skeleton

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import android.view.KeyEvent.KEYCODE_BACK



class HelpfeelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helpfeel)
        val webview: WebView = findViewById<WebView>(R.id.helpfeel_webview)
        webview.setWebViewClient(WebViewClient())
        webview.clearHistory()
        webview.getSettings().setJavaScriptEnabled(true)
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true)
        webview.loadUrl("https://helpfeel.notainc.com/SFCHelp")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val webview: WebView = findViewById<WebView>(R.id.helpfeel_webview)
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
