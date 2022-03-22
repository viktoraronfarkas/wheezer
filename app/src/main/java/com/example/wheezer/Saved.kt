package com.example.wheezer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_explore.*

class Saved : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        // get saved exercises from DB, and display them on cards
        val db = DBHelper(this, null)
        val cursor = db.getSavedExercises()
        cursor!!.moveToFirst()
        val count = cursor.count
        if (count > 0) {
            val id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL))
            val name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME))

            addCard(id.toInt(), name)

            while(cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL))
                val name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME))
                addCard(id.toInt(), name)
            }
        }

        cursor.close()

        // NAVBAR
        navigation.inflateMenu(R.menu.nav_menu)
        navigation.selectedItemId = R.id.menuSaved

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
    }

    private fun addCard(id: Int, text: String) {
        // add exercise card with its layout parameters
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

        val layoutParams = LinearLayout.LayoutParams(
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
            val intent = Intent(this@Saved, StartExercise::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    fun dpToPx(dp: Float, context: Context): Int {
        // convert DPs to pixels
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()).toInt()
    }
}