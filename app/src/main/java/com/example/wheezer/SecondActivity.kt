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

        // add default exercises
        db.addExercise(
            "5-5-5",
            "Stressed",
            "inhale:5,exhale:5,hold:5,inhale:5,exhale:5,hold:5,inhale:5,exhale:5,hold:5,inhale:5,exhale:5,hold:5",
            1
        )
        db.addExercise(
            "4-7-8",
            "Stressed",
            "inhale:4,hold:7,exhale:8,inhale:4,hold:7,exhale;8,inhale:4,hold:7,exhale;8",
            1
        )
        db.addExercise(
            "Quick calm",
            "Panicked",
            "inhale:3,exhale:3,inhale:4,exhale:4,inhale:5,exhale:5,inhale:6,exhale:6,inhale:7,hold:7exhale:7",
            1
        )
        db.addExercise(
            "Anti-Stress",
            "Stressed",
            "inhale:5,exhale:5,inhale:5,exhale:5,inhale:5,exhale:5,inhale:8,hold:9,exhale:10",
            1
        )
        db.addExercise(
            "Calm your nerves",
            "Panicked",
            "inhale:1,exhale:1,inhale:2,exhale:2,inhale:3,exhale:3,inhale:4,hold:5,exhale:6",
            1
        )
        db.addExercise(
            "Quick relief",
            "Panicked",
            "inhale:5,exhale:5,inhale:5,exhale:5,inhale:5,exhale:5",
            1
        )
        db.addExercise(
            "Anti-Anxiety",
            "Anxious",
            "inhale:3,exhale:3,inhale:3,exhale:3,inhale:5,hold:5,exhale:5",
            1
        )
        db.addExercise(
            "Relax, my friend",
            "Anxious",
            "inhale:2,exhale:2,inhale:3,exhale:3,inhale:4,exhale:4,inhale:5,exhale:5,inhale:8,exhale:4",
            1
        )
        db.addExercise(
            "Crush Anxiety",
            "Anxious",
            "inhale:1,exhale:1,inhale:5,exhale:5,inhale:2,exhale:2,inhale:5,exhale:5,inhale:3,exhale:3,inhale:8,exhale:4",
            1
        )
        db.addExercise(
            "Free diving warmup",
            "Lung capacity",
            "inhale:5,hold:2,exhale:10,hold:2,inhale:5,hold:2,exhale:10,hold:2,inhale:5,hold:2,exhale:10,hold:2,inhale:5,hold:2,exhale:10,hold:2,inhale:5,hold:2,exhale:10,hold:2",
            1
        )
        db.addExercise(
            "Long hold",
            "Lung capacity",
            "inhale:2,exhale:2,inhale:2,exhale:2,inhale:2,exhale:2,inhale:8,exhale:3,inhale:9,exhale:3,inhale:10,exhale:3,inhale:1,exhale:1,inhale:1,exhale:1,inhale:1,exhale:1,inhale:8,hold:60,exhale:3",
            1
        )
        db.addExercise(
            "Impossible",
            "Misc",
            "inhale:1,hold:50,exhale:1",
            1
        )
        Toast.makeText(this, "New entries added to database", Toast.LENGTH_LONG).show()

        // navigate to ThirdActivity after 5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)

        supportActionBar?.hide()
    }
}