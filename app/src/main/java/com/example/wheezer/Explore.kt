package com.example.wheezer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
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


            while(cursorCategory.moveToNext()) {
                val id = cursorCategory.getString(cursorCategory.getColumnIndex(DBHelper.ID_COL))
                val name = cursorCategory.getString(cursorCategory.getColumnIndex(DBHelper.NAME))
                addCard(id.toInt(), name)
            }
        }

        cursorCategory.close()
    }

    private fun addCard(id: Int, text: String) {

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
            val intent = Intent(this@Explore, Explore::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()).toInt()
    }
}