package com.example.wheezer

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)


        progressBar.max = 100000

        val currentProgress = 60000

        ObjectAnimator.ofInt(progressBar,"progress", currentProgress)
            .setDuration(2000)
            .start()
    }
}