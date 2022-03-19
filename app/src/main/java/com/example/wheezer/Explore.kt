package com.example.wheezer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView

class Explore : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        val db = DBHelper(this, null)
        val cursorCategory = db.getCategories()
        cursorCategory!!.moveToFirst()
        val count = cursorCategory.count
        if (count > 0) {
            val currentCategory = cursorCategory.getString(cursorCategory.getColumnIndex(DBHelper.CAT))
            addCategorySection(currentCategory)

            while(cursorCategory.moveToNext()) {
                val currentCategory = cursorCategory.getString(cursorCategory.getColumnIndex(DBHelper.CAT))
                addCategorySection(currentCategory)
            }
        }

        cursorCategory.close()
    }

    @SuppressLint("Range")
    private fun addCategorySection(category: String) {
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        val title = TextView(this)
        title.text = category
        title.textSize = dpToPx(9f, applicationContext).toFloat()
        title.setTextColor(Color.WHITE)
        val titleParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        titleParams.topMargin = dpToPx(60f, applicationContext)
        titleParams.leftMargin = dpToPx(40f, applicationContext)
        title.layoutParams = titleParams

        linearLayout.addView(title)

        val gridLayout = GridLayout(this)
        val gridParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, // CardView width
            ViewGroup.LayoutParams.WRAP_CONTENT // CardView height
        )
        gridLayout.layoutParams = gridParams
        gridLayout.columnCount = 2
        linearLayout.addView(gridLayout)

        val db = DBHelper(this, null)
        val cursor = db.getExercisesByCategory(category)
        cursor!!.moveToFirst()
        val count = cursor.count
        if (count > 0) {
            val id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL))
            val name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME))

            addCard(id.toInt(), name, gridLayout)

            while(cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL))
                val name = cursor.getString(cursor.getColumnIndex(DBHelper.NAME))
                addCard(id.toInt(), name, gridLayout)
            }
        }

        cursor.close()

    }

    private fun addCard(id: Int, text: String, context: GridLayout) {

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
        context.addView(cardView)

        cardView.setOnClickListener {
            val intent = Intent(this@Explore, StartExercise::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()).toInt()
    }
}