package com.example.wheezer

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME + " TEXT UNIQUE," +
                CAT + " TEXT," +
                EXERCISE_DATA + " TEXT," +
                IS_DEFAULT + " INTEGER," +
                IS_SAVED + " INTEGER" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addExercise(name : String, category : String, exercise_data: String, is_default: Int ): Boolean {

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(NAME, name)
        values.put(CAT, category)
        values.put(EXERCISE_DATA, exercise_data)
        values.put(IS_DEFAULT, is_default)
        values.put(IS_SAVED, 0)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // catch errors
        try {
            db.insertOrThrow(TABLE_NAME, null, values)
        } catch (e: SQLiteException) {
            return false
        } finally {
            return true
            db.close()
        }
    }

    fun updateExercise(id: String, name: String):
        Boolean {
            //this method updates an existing exercise
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(ID_COL, id)
            contentValues.put(NAME, name)
            db.update(TABLE_NAME, contentValues, "id = ?", arrayOf(id))
            return true
        }

    fun deleteExercise(id : String) : Int {
        //this method deletes an existing exercise
        val db = this.writableDatabase
        return db.delete(TABLE_NAME,"id = ?", arrayOf(id))
    }

    fun getExerciseById (id : Int): Cursor? {
        // this method returns the cursor for a single exercise of given ID
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE id=$id", null)
    }

    fun getForYouExercises(category: String): Cursor? {
        // This method gets exercises for the For You page by category with a limit of 3
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE category='$category' LIMIT 3", null)
    }

    fun getCategories (): Cursor? {
        // This method returns the list of categories
        val db = this.readableDatabase
        return db.rawQuery("SELECT DISTINCT category FROM $TABLE_NAME", null)
    }

    fun getExercisesByCategory (category: String): Cursor? {
        // This method returns exercises by a single category
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE category='$category'", null)
    }

    fun saveExercise(id: String, value: Int): Boolean {
        // This method flips the IS_SAVED flag for an exercise. 0 is not saved, 1 is saved.
        val db = this.readableDatabase
        val contentValues = ContentValues()
        contentValues.put(IS_SAVED, value)
        try {
            db.update(TABLE_NAME, contentValues, "id = ?", arrayOf(id))
        } catch (e: SQLiteException) {
            return false
        } finally {
            return true
            db.close()
        }
    }

    fun getSavedExercises(): Cursor? {
        // This method returns all saved exercises.
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE is_saved=1", null)
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private const val DATABASE_NAME = "wheezer"

        // below is the variable for database version
        private const val DATABASE_VERSION = 1

        // below is the variable for table name
        const val TABLE_NAME = "exercises"

        // below is the variable for id column
        const val ID_COL = "id"

        // below is the variable for name column
        const val NAME = "name"

        // below is the variable for category column
        const val CAT = "category"

        // below is the variable for exercise data column
        const val EXERCISE_DATA = "exercise_data"

        // below is the variable for is_default data column
        const val IS_DEFAULT = "is_default"

        // below is the variable for is_saved data column
        const val IS_SAVED = "is_saved"
    }
}