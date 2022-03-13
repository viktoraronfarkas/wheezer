package com.example.wheezer

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        val newList = ArrayList<ArrayList<String>>()
        for (item in list) {
            newList.add(ArrayList(item.split(":")))
        }

        var currentExerciseType: String = ""
        var currentExerciseDuration: Long = 0

        motivation_text.text = newList.size.toString()
        currentExerciseType = newList[0][0]
        currentExerciseDuration = newList[0][1].toLong() * 1000
        exercise_type.text = currentExerciseType

        Handler(Looper.getMainLooper()).postDelayed({
            currentExerciseType = newList[1][0]
            currentExerciseDuration = newList[1][1].toLong() * 1000
            exercise_type.text = currentExerciseType
        }, currentExerciseDuration)

        Handler(Looper.getMainLooper()).postDelayed({
            currentExerciseType = newList[2][0]
            currentExerciseDuration = newList[2][1].toLong() * 1000
            exercise_type.text = currentExerciseType
        }, currentExerciseDuration)

        Handler(Looper.getMainLooper()).postDelayed({
            currentExerciseType = newList[3][0]
            currentExerciseDuration = newList[3][1].toLong() * 1000
            exercise_type.text = currentExerciseType
        }, currentExerciseDuration)

        Toast.makeText(this@ExerciseActivity, "Exercise finished!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@ExerciseActivity, Explore::class.java)
        startActivity(intent)
    }
}