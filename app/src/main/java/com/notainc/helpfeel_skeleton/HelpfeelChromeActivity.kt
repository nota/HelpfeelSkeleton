package com.notainc.helpfeel_skeleton

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class HelpfeelChromeActivity : AppCompatActivity() {
    private var helpfeelUrl: String

    init {
        this.helpfeelUrl = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helpfeel_chrome)
    }
}