package com.example.wheezer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class ForYouPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_you_page)

        Toast.makeText(this@ForYouPage, intent.getStringExtra("Category"), Toast.LENGTH_SHORT).show()

        val db = DBHelper(this, null)
        val cursor = db.getExercises()
        cursor!!.moveToFirst()


    }
}