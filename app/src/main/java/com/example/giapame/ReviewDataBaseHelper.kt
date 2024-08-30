package com.example.giapame

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ReviewDataBaseHelper(context: Context) : SQLiteOpenHelper(context, "reviews.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        // Create the reviews table
        db.execSQL("CREATE TABLE reviews (_id INTEGER PRIMARY KEY, product_id INT, review TEXT, rating INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades
        db.execSQL("DROP TABLE IF EXISTS reviews")
        onCreate(db)
    }
}