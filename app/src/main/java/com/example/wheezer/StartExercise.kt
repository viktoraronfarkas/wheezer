package com.example.wheezer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import android.view.View

class StartExercise : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_exercise)

        val exerciseTitle = findViewById<TextView>(R.id.exerciseTitle)
        val buttonDelete = findViewById<MaterialButton>(R.id.button_delete)
        val buttonEdit = findViewById<MaterialButton>(R.id.button_edit)
        val buttonStart = findViewById<MaterialButton>(R.id.button_start)

        val db = DBHelper(this, null)
        val currentId = intent.getIntExtra("id", 0)
        val cursor = db.getExerciseById(currentId)

        cursor!!.moveToFirst()
        if(cursor.count > 0)
        {
            val name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME))
            exerciseTitle.text = name

            if(cursor.getInt(cursor.getColumnIndex(DBHelper.IS_DEFAULT)) == 1) {
                disableButton(buttonDelete)
                disableButton(buttonEdit)
            }
        }

        cursor.close()

        buttonDelete.setOnClickListener {
            clickDelete(currentId.toString())
        }

        val newName = "Test"
        buttonEdit.setOnClickListener {
            clickEdit(currentId.toString(), newName)
        }

        buttonStart.setOnClickListener {
            clickStart(currentId)
        }
    }

    private fun clickStart(id: Int) {
        val intent = Intent(this@StartExercise, ExerciseActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun clickEdit(id: String, name: String) {
        val db = DBHelper(this, null)
        db.updateExercise(id, name)
        Toast.makeText(this@StartExercise, "Exercise updated.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@StartExercise, Explore::class.java)
        startActivity(intent)
    }

    private fun clickDelete(id: String) {
        val db = DBHelper(this, null)
        db.deleteExercise(id)
        Toast.makeText(this@StartExercise, "Exercise deleted.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@StartExercise, Explore::class.java)
        startActivity(intent)
    }

    private fun disableButton(button: Button) {
        button.isEnabled = false
        button.setTextColor(ContextCompat.getColor(this,R.color.white))
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.grey))

        val buttonSave = findViewById<ImageButton>(R.id.saveButton)
        buttonSave.setOnClickListener {
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent)


    }
}