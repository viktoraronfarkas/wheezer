package com.example.wheezer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class CreateYourExercise : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_your_exercise)
    }
    fun getSet(view: View)
    {
        val editTxt = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.exercise_title)
        val msg= editTxt.text.toString();

        /*val customExercise= findViewById<TextView>(R.id.yourExercise).apply {
            text = msg
        }*/

    }
}