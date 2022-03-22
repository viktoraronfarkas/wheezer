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
import kotlinx.android.synthetic.main.activity_explore.*


class ExerciseActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        //get ID of current exercise
        val currentId = intent.getIntExtra("id", 0)

        //NAVBAR
        navigation_exercise.inflateMenu(R.menu.nav_menu)

        navigation_exercise.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuExplore -> {
                    startActivity(
                        Intent(
                            this,
                            Explore::class.java
                        )
                    )
                }
                R.id.menuSaved -> {
                    startActivity(
                        Intent(
                            this,
                            Saved::class.java
                        )
                    )
                }
                R.id.menuNewExercise -> {
                    startActivity(Intent(
                        this,
                        CreateYourExercise::class.java
                    ))
                }
            }
            true
        }

        // Get data from DB
        val db = DBHelper(this, null)
        val cursor = db.getExerciseById(currentId)
        cursor!!.moveToFirst()

        // check if method returns result
        val count = cursor.count
        var exerciseData = ""
        if (count > 0) {
            exerciseData = cursor.getString(cursor.getColumnIndex(DBHelper.EXERCISE_DATA))
        }
        cursor.close()

        // Split and copy exercise_data string into a 2 Dimensional array
        val list = exerciseData.split(",")
        val exerciseList = ArrayList<ArrayList<String>>()
        for (item in list) {
            exerciseList.add(ArrayList(item.split(":")))
        }

        // add random motivational message to every step from source file, shuffle them for randomness
        val motivationStrings: Array<String> = resources.getStringArray(R.array.motivational_strings)
        motivationStrings.shuffle()

        animate(0, exerciseList, motivationStrings)
    }

    private fun animate(i : Int, exercises: ArrayList<ArrayList<String>>, motivationStrings: Array<String>) {
        // this is a recursive function to iterate through the 2D Array, and display each step while animating the progress bar
        if (i < exercises.size) {
            val currentExerciseType = exercises[i][0]
            val currentExerciseDuration = exercises[i][1].toLong() * 1000
            exercise_type.text = currentExerciseType
            // start motivation string array over if there are more exercises than motivational strings defined
            motivation_text.text = motivationStrings[i % motivationStrings.size]

            // progress bar animation
            val animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
            animation.duration = currentExerciseDuration

            animation.interpolator = DecelerateInterpolator()
            animation.start()

            animation.doOnEnd {
                if (i < exercises.size) {
                    // call method recursively if there are still exercises in the array left
                    animate(i + 1, exercises, motivationStrings)
                }
            }
        } else if (i == exercises.size) {
            // if iterator reaches the last exercise, navigate out of page
            Toast.makeText(this@ExerciseActivity, "Exercise finished. Good job!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@ExerciseActivity, Explore::class.java)
            startActivity(intent)
        }
    }
}