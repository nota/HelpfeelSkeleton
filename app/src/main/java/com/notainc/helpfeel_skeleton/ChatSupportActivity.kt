package com.notainc.helpfeel_skeleton

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
// import android.widget.Toast

class ChatSupportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_support)

        this.setToolbar()
    }

    fun onClickNav() {
        // Toast.makeText(this@ChatSupportActivity, "back click!!", Toast.LENGTH_SHORT).show()
        this.finish()
    }

    fun onClickNavMenu(): Boolean {
        this.finish()
        return true
    }

    fun setToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_chat_support)
        toolbar.setBackgroundColor(intent.getIntExtra("todayColorPrimary", Color.GRAY))

        toolbar.inflateMenu(R.menu.chat_support)
        toolbar.setOnMenuItemClickListener{ onClickNavMenu() }
    }
}
