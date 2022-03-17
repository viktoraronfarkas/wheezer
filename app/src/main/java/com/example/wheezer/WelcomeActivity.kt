package com.example.wheezer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.wheezer.DBHelper

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@WelcomeActivity, SecondActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

        supportActionBar?.hide()
    }
}