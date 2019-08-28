package com.notainc.helpfeel_skeleton

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.MenuItem
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.content.ContextCompat.checkSelfPermission


class HelpfeelActivity : AppCompatActivity() {
    private var helpfeelUrl: String
    private var toolbarBgColor: Int

    private var PERMISSIONS_AT_WEBVIEW = 0

    init {
        this.helpfeelUrl = ""
        this.toolbarBgColor = Color.GRAY
    }

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helpfeel)

        this.helpfeelUrl = intent.getStringExtra("webViewUrl")
        this.toolbarBgColor = intent.getIntExtra("todayColorPrimary", this.toolbarBgColor)

        this.setToolbar()
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_AT_WEBVIEW -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // yay!
                    val webview: WebView = findViewById(R.id.helpfeel_webview)
                    webview.reload()
                }
            }
        }
        return
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

        val activity = this
        webview.webChromeClient = object: WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                val permissionCheck = checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(activity, arrayOf(Manifest.permission.RECORD_AUDIO), PERMISSIONS_AT_WEBVIEW)
                } else {
                    request.grant(request.resources)
                }
            }
        }

        webview.settings.javaScriptEnabled = true
        webview.settings.javaScriptCanOpenWindowsAutomatically = true
        WebView.setWebContentsDebuggingEnabled(true)
        webview.loadUrl(this.helpfeelUrl)
    }

    fun setToolbar () {
        val toolbar: Toolbar = findViewById(R.id.toolbar_helpfeel)
        toolbar.setBackgroundColor(this.toolbarBgColor)
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
        intent.putExtra("todayColorPrimary", this.toolbarBgColor)
        startActivity(intent)
    }
}
