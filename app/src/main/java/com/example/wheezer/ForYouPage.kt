package com.example.wheezer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import android.widget.Toast
import android.widget.LinearLayout
import android.widget.GridLayout
import androidx.cardview.widget.CardView
import android.widget.LinearLayout.LayoutParams
import kotlinx.android.synthetic.main.activity_explore.*

class ForYouPage : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_you_page)

        Toast.makeText(this@ForYouPage, intent.getStringExtra("Category"), Toast.LENGTH_SHORT).show()

        //get 3 exercises from DB by category, and add the cards
        val db = DBHelper(this, null)
        val cursor = intent.getStringExtra("Category")?.let { db.getForYouExercises(it) }
        cursor!!.moveToFirst()
        val id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL))
        val name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME))

        addCard(id.toInt(), name)

        while(cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL))
            val name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME))
            addCard(id.toInt(), name)
        }

        cursor.close()

        // NAVBAR
        navigation.inflateMenu(R.menu.nav_menu)

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

        addNewExerciseCard()
    }

    private fun addCard(id: Int, text: String) {
        //add exercise card, and its layout parameters
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        val cardLinearLayout = LinearLayout(this)

        val cardView = CardView(this)
        cardView.radius = dpToPx(12f, applicationContext).toFloat()
        cardView.setContentPadding(
            dpToPx(16f, applicationContext),
            dpToPx(16f, applicationContext),
            dpToPx(16f, applicationContext),
            dpToPx(16f, applicationContext)
        )
        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        cardView.cardElevation = dpToPx(6f, applicationContext).toFloat()

        val layoutParams = LayoutParams(
            dpToPx(130f, applicationContext), // CardView width
            dpToPx(96f, applicationContext) // CardView height
        )

        layoutParams.topMargin = dpToPx(35f, applicationContext)
        layoutParams.bottomMargin = dpToPx(35f, applicationContext)
        layoutParams.leftMargin = dpToPx(35f, applicationContext)
        layoutParams.rightMargin = dpToPx(35f, applicationContext)
        cardView.layoutParams = layoutParams
        val exerciseName = TextView(this)
        exerciseName.text = text
        exerciseName.textSize = dpToPx(6f, applicationContext).toFloat()
        cardLinearLayout.addView(exerciseName)
        cardView.addView(cardLinearLayout)
        gridLayout.addView(cardView)

        cardView.setOnClickListener {
            val intent = Intent(this@ForYouPage, StartExercise::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    private fun addNewExerciseCard() {
        // add "add new exercise" card, and its layout parameters
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        val cardLinearLayout = LinearLayout(this)

        val cardView = CardView(this)
        cardView.radius = dpToPx(12f, applicationContext).toFloat()
        cardView.setContentPadding(
            dpToPx(16f, applicationContext),
            dpToPx(16f, applicationContext),
            dpToPx(16f, applicationContext),
            dpToPx(16f, applicationContext)
        )
        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        cardView.cardElevation = dpToPx(6f, applicationContext).toFloat()

        val layoutParams = LayoutParams(
            dpToPx(130f, applicationContext), // CardView width
            dpToPx(96f, applicationContext) // CardView height
        )

        layoutParams.topMargin = dpToPx(35f, applicationContext)
        layoutParams.bottomMargin = dpToPx(35f, applicationContext)
        layoutParams.leftMargin = dpToPx(35f, applicationContext)
        layoutParams.rightMargin = dpToPx(35f, applicationContext)
        cardView.layoutParams = layoutParams
        val exerciseName = TextView(this)
        exerciseName.text = "+"
        exerciseName.textSize = dpToPx(6f, applicationContext).toFloat()
        cardLinearLayout.addView(exerciseName)
        cardView.addView(cardLinearLayout)
        gridLayout.addView(cardView)

        cardView.setOnClickListener {
            val intent = Intent(this@ForYouPage, CreateYourExercise::class.java)
            startActivity(intent)
        }
    }

    fun dpToPx(dp: Float, context: Context): Int {
        // convert DP to pixels
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()).toInt()
    }
}