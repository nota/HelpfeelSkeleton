package com.notainc.helpfeel_skeleton

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.support.v7.widget.Toolbar
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.view.LayoutInflater
import android.widget.EditText


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var todayColorPrimary: Int
    var webViewUrl: String

    init {
        todayColorPrimary = Color.GRAY
        // webViewUrl = "https://helpfeel.notainc.com"
        webViewUrl = "https://helpfeel.notainc.com/SFCHelp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.todayColorPrimary = getColor(R.color.colorPrimary)

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_red -> {
                this.updateTodayColorPrimary(Color.parseColor("#F44336"))
                return true
            }
            R.id.action_green -> {
                this.updateTodayColorPrimary(Color.parseColor("#388E3C"))
                return true
            }
            R.id.action_blue -> {
                this.updateTodayColorPrimary(Color.parseColor("#0336FF"))
                return true
            }
            R.id.action_blue_gray -> {
                this.updateTodayColorPrimary(Color.parseColor("#546E7A"))
                return true
            }
            R.id.action_set_webview_url -> {
                this.showDialogAndUpdateWebViewUrl()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_helpfeel_chrome -> {
                this.startChromeCustomTabsIntent()
            }
            R.id.nav_helpfeel -> {
                val intent = Intent(this, HelpfeelActivity::class.java)
                intent.putExtra("todayColorPrimary", this.todayColorPrimary)
                intent.putExtra("webViewUrl", this.webViewUrl)
                startActivity(intent)
            }
            R.id.nav_chat_support -> {
                val intent = Intent(this, ChatSupportActivity::class.java)
                intent.putExtra("todayColorPrimary", this.todayColorPrimary)
                startActivity(intent)
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun showDialogAndUpdateWebViewUrl () {
        val layoutInflater = LayoutInflater.from(this)
        val promptWebViewUrl = layoutInflater.inflate(R.layout.dialog_prompt, null)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(promptWebViewUrl)

        val input = promptWebViewUrl.findViewById<EditText>(R.id.input_webview_url)
        input.setText(this.webViewUrl)
        alertDialogBuilder.setPositiveButton("Ok") { dialog, id ->
            val url = input.getText()
            if (url.isNotEmpty()) this.webViewUrl = url.toString()
        }

        alertDialogBuilder.setTitle("WebView URL:")
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    fun updateTodayColorPrimary(color: Int) {
        this.todayColorPrimary = color
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setBackgroundColor(color)
        window.setStatusBarColor(color)
        val drawerBg: LinearLayout = findViewById(R.id.drawer_bg)
        drawerBg.setBackgroundColor(color)
    }

    fun startChromeCustomTabsIntent() {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(this.webViewUrl))
    }
}
