package com.notainc.helpfeel_skeleton

import android.app.DownloadManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient

class HelpfeelActivity : AppCompatActivity() {
    private val helpfeelUrl: String

    init {
        this.helpfeelUrl = "https://helpfeel.notainc.com/SFCHelp"
    }

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helpfeel)

        this.setToolbarMenus()
        this.setWebView()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val webview: WebView = findViewById(R.id.helpfeel_webview)
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    fun setWebView() {
        val webview: WebView = findViewById(R.id.helpfeel_webview)
        webview.clearHistory()

        webview.webViewClient = object: WebViewClient() {
            override fun shouldOverrideUrlLoading (view: WebView, url: String): Boolean {
                // /product等への遷移を奪って、ChatSupportを開き、自身を閉じる
                if (url.endsWith("/product") || url.endsWith("/help-jp/")) {
                    webview.stopLoading()
                    startChatSupport().apply{}
                    finish().apply{}
                    return true
                }
                return false
            }
        }

        webview.settings.javaScriptEnabled = true
        webview.settings.javaScriptCanOpenWindowsAutomatically = true
        webview.loadUrl(this.helpfeelUrl)
    }

    fun setToolbarMenus () {
        val toolbar: Toolbar = findViewById(R.id.toolbar_helpfeel)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { onClickNav() }
        toolbar.inflateMenu(R.menu.helpfeel)
        toolbar.setOnMenuItemClickListener{ item -> onClickNavMenu(item) }
    }

    fun onClickNav() {
        this.finish()
    }

    fun onClickNavMenu(item: MenuItem): Boolean {
        val id: Int = item.itemId
        // ChatSupportを開いて、自身を閉じる
        if (id == R.id.action_helpfeel_close) {
            this.startChatSupport()
            this.finish()
        }
        return true
    }

    fun startChatSupport() {
        val intent = Intent(applicationContext, ChatSupportActivity::class.java)
        startActivity(intent)
    }
}
