package com.example.wheezer

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import kotlinx.android.synthetic.main.activity_exercise.*


class ExerciseActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        val currentId = intent.getIntExtra("id", 0)

        val db = DBHelper(this, null)
        val cursor = db.getExerciseById(currentId)
        cursor!!.moveToFirst()
        val count = cursor.count
        var exerciseData = ""
        if (count > 0) {
            exerciseData = cursor.getString(cursor.getColumnIndex(DBHelper.EXERCISE_DATA))
        }
        cursor.close()

        val list = exerciseData.split(",")
        val exerciseList = ArrayList<ArrayList<String>>()
        for (item in list) {
            exerciseList.add(ArrayList(item.split(":")))
        }

        val motivationStrings: Array<String> = resources.getStringArray(R.array.motivational_strings)
        motivationStrings.shuffle()

        animate(0, exerciseList, motivationStrings)
    }

    private fun animate(i : Int, exercises: ArrayList<ArrayList<String>>, motivationStrings: Array<String>) {
        if (i < exercises.size) {
            val currentExerciseType = exercises[i][0]
            val currentExerciseDuration = exercises[i][1].toLong() * 1000
            exercise_type.text = currentExerciseType
            motivation_text.text = motivationStrings[i % motivationStrings.size]

            val animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
            animation.duration = currentExerciseDuration

            animation.interpolator = DecelerateInterpolator()
            animation.start()

            animation.doOnEnd {
                if (i < exercises.size) {
                    animate(i + 1, exercises, motivationStrings)
                }
            }
        } else if (i == exercises.size) {
            Toast.makeText(this@ExerciseActivity, "Exercise finished. Good job!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@ExerciseActivity, Explore::class.java)
            startActivity(intent)
        }
    }
}