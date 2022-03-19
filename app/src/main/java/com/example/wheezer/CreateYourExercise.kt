package com.example.wheezer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class CreateYourExercise : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_your_exercise)

        val add_btn = findViewById<Button>(R.id.addExerciseButton)
        val db = DBHelper(this, null)



        add_btn.setOnClickListener {
            val title = findViewById<EditText>(R.id.editTitle).text.toString()
            val cat = findViewById<EditText>(R.id.editCategory).text.toString()
            val exercise = findViewById<EditText>(R.id.editExercise).text.toString()

            db.addExercise(title, cat, exercise, 0)
            Toast.makeText(this, "New exercise added to database.", Toast.LENGTH_LONG).show()

            val intent = Intent(this@CreateYourExercise, Explore::class.java)
            startActivity(intent)
        }
    }
}