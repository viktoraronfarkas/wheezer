package com.example.wheezer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class StartExercise : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_exercise)

        val buttonSave = findViewById<ImageButton>(R.id.saveButton)
        buttonSave.setOnClickListener {
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent)


    }
}