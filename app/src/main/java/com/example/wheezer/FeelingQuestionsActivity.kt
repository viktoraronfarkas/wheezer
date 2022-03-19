package com.example.wheezer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class FeelingQuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeling_questions)

        val stressed_btn = findViewById<Button>(R.id.stressed_btn)
        val anxious_btn = findViewById<Button>(R.id.anxious_btn)
        val panicked_btn = findViewById<Button>(R.id.panicked_btn)

        stressed_btn.setOnClickListener {
            Toast.makeText(this@FeelingQuestionsActivity, "You clicked stressed.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@FeelingQuestionsActivity, ForYouPage::class.java)
            intent.putExtra("Category", "Stressed")
            startActivity(intent)
        }

        anxious_btn.setOnClickListener {
            Toast.makeText(this@FeelingQuestionsActivity, "You clicked anxious.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@FeelingQuestionsActivity, ForYouPage::class.java)
            intent.putExtra("Category", "Anxious")
            startActivity(intent)
        }

        panicked_btn.setOnClickListener {
            Toast.makeText(this@FeelingQuestionsActivity, "You clicked panicked.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@FeelingQuestionsActivity, ForYouPage::class.java)
            intent.putExtra("Category", "Panicked")
            startActivity(intent)
        }
    }
}

