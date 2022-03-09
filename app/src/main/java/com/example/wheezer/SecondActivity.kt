package com.example.wheezer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val db = DBHelper(this, null)
        db.addExercise(
            "5-5-5",
            "Calm",
            "inhale:5,hold:5,exhale:5,inhale:5,hold:5,exhale:5,inhale:5,hold:5,exhale:5,inhale:5,hold:5,exhale:5,inhale:5,hold:5,exhale:5"
        )
        db.addExercise(
            "4-7-8",
            "Sleep",
            "inhale:4,hold:7,exhale:8,inhale:4,hold:7,exhale:8,inhale:4,hold:7,exhale:8"
        )
        db.addExercise(
            "Quick calm",
            "Calm",
            "inhale:5,exhale:5,inhale:5,exhale:5,inhale:5,exhale:5,inhale:5,hold:5,exhale:5"
        )
        Toast.makeText(this, "New entries added to database", Toast.LENGTH_LONG).show()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)

        supportActionBar?.hide()
    }
}