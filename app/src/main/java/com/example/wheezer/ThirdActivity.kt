package com.example.wheezer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@ThirdActivity, Saved::class.java)
            startActivity(intent)
            finish()
        }, 3000)

        supportActionBar?.hide()
    }
}