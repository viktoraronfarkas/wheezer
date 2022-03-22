package com.example.wheezer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_explore.*

class StartExercise : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_exercise)

        // define layout elements as variables
        val exerciseTitle = findViewById<TextView>(R.id.exerciseTitle)
        val buttonDelete = findViewById<MaterialButton>(R.id.button_delete)
        val buttonEdit = findViewById<MaterialButton>(R.id.button_edit)
        val buttonStart = findViewById<MaterialButton>(R.id.button_start)
        val textEditTitle = findViewById<EditText>(R.id.editTitle)
        val buttonSaveEdit = findViewById<MaterialButton>(R.id.button_save_edit)
        val buttonSave = findViewById<ImageButton>(R.id.saveButton)
        val buttonSaveFilled = findViewById<ImageButton>(R.id.saveButtonFilled)

        // get current exercise data
        val db = DBHelper(this, null)
        val currentId = intent.getIntExtra("id", 0)
        val cursor = db.getExerciseById(currentId)

        cursor!!.moveToFirst()
        if(cursor.count > 0)
        {
            // set exercise title
            val name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME))
            exerciseTitle.text = name

            // if exercise is default, disable edit and delete buttons
            if (cursor.getInt(cursor.getColumnIndex(DBHelper.IS_DEFAULT)) == 1) {
                disableButton(buttonDelete)
                disableButton(buttonEdit)
            }

            // fill / unfill saved icon according to whether the exercise is saved or not
            if (cursor.getInt(cursor.getColumnIndex(DBHelper.IS_SAVED)) == 0) {
                buttonSave.visibility = View.VISIBLE
                buttonSaveFilled.visibility = View.GONE
            } else if (cursor.getInt(cursor.getColumnIndex(DBHelper.IS_SAVED)) == 1) {
                buttonSave.visibility = View.GONE
                buttonSaveFilled.visibility = View.VISIBLE
            }
        }

        cursor.close()

        // Click Listeners
        buttonDelete.setOnClickListener {
            clickDelete(currentId.toString())
        }

        buttonEdit.setOnClickListener {
            // show exercise title edit field
            textEditTitle.visibility = View.VISIBLE
            buttonSaveEdit.visibility = View.VISIBLE
        }

        buttonSaveEdit.setOnClickListener {
            // save edited title to DB
            val newTitle = textEditTitle.text.toString()
            clickEdit(currentId.toString(), newTitle)
        }

        buttonStart.setOnClickListener {
            clickStart(currentId)
        }

        buttonSave.setOnClickListener {
            // save exercise
            saveExercise(currentId, 1)
        }

        buttonSaveFilled.setOnClickListener {
            // unsave exercise
            saveExercise(currentId, 0)
        }

        // NAVBAR
        navigation.inflateMenu(R.menu.nav_menu)
        navigation.selectedItemId = R.id.menuNewExercise

        navigation.setOnItemSelectedListener {
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
                    startActivity(Intent(
                        this,
                        Saved::class.java
                    ))
                }
                R.id.menuNewExercise -> {

                }
            }
            true
        }

    }

    private fun saveExercise(id: Int, value: Int) {
        // save exercise ( IS_SAVED )
        val db = DBHelper(this, null)
        db.saveExercise(id.toString(), value)

        val intent = Intent(this@StartExercise, StartExercise::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun clickStart(id: Int) {
        // navigate to exercise activity
        val intent = Intent(this@StartExercise, ExerciseActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun clickEdit(id: String, name: String) {
        // edit exercise
        val db = DBHelper(this, null)
        db.updateExercise(id, name)
        Toast.makeText(this@StartExercise, "Exercise updated.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@StartExercise, Explore::class.java)
        startActivity(intent)
    }

    private fun clickDelete(id: String) {
        // delete exercise
        val db = DBHelper(this, null)
        db.deleteExercise(id)
        Toast.makeText(this@StartExercise, "Exercise deleted.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@StartExercise, Explore::class.java)
        startActivity(intent)
    }

    private fun disableButton(button: Button) {
        // disable buttons, change color to gray
        button.isEnabled = false
        button.setTextColor(ContextCompat.getColor(this,R.color.white))
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.grey))
    }
}