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
            "Stressed",
            "inhale:5,hold:5,exhale:5,inhale:5",
            1
        )
        db.addExercise(
            "4-7-8",
            "Stressed",
            "inhale:4,hold:7,exhale:8,inhale:4",
            1
        )
        db.addExercise(
            "Quick calm",
            "Stressed",
            "inhale:5,exhale:5,inhale:5,exhale:5",
            1
        )
        db.addExercise(
            "Anti-Panic",
            "Panicked",
            "inhale:5,exhale:5,inhale:5,exhale:5",
            1
        )
        db.addExercise(
            "Chill",
            "Panicked",
            "inhale:5,exhale:5,inhale:5,exhale:5",
            1
        )
        db.addExercise(
            "Quick relief",
            "Panicked",
            "inhale:5,exhale:5,inhale:5,exhale:5",
            1
        )
        db.addExercise(
            "Anti-Anxiety",
            "Anxious",
            "inhale:5,exhale:5,inhale:5,exhale:5",
            1
        )
        db.addExercise(
            "Relax, my friend",
            "Anxious",
            "inhale:5,exhale:5,inhale:5,exhale:5",
            1
        )
        db.addExercise(
            "Crush Anxiety",
            "Anxious",
            "inhale:5,exhale:5,inhale:5,exhale:5",
            1
        )
        db.addExercise(
            "Test",
            "Test Category",
            "step1:5,step2:5,step3:5,step4:5",
            1
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