package com.notainc.helpfeel_skeleton

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Toast

class ChatSupportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_support)

        this.setToolbarMenus()
    }

    fun onClickNav() {
        // Toast.makeText(this@ChatSupportActivity, "back click!!", Toast.LENGTH_SHORT).show()
        this.finish()
    }

    fun onClickNavMenu(): Boolean {
        this.finish()
        return true
    }

    fun setToolbarMenus() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_chat_support)
        // 戻るボタン
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener { onClickNav() }
        // メニュー
        toolbar.inflateMenu(R.menu.chat_support)
        toolbar.setOnMenuItemClickListener{ onClickNavMenu() }
    }
}
