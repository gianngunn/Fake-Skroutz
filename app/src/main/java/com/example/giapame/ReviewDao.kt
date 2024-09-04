package com.example.giapame

import android.content.ContentValues
import android.database.Cursor
import kotlin.math.roundToInt


class ReviewDao(private val dbHelper: ReviewDataBaseHelper) {
    fun getReviewsForProduct(productId: Int): MutableList<ProductReviews> {
        val db = dbHelper.readableDatabase
        val cursor = db.query("reviews", arrayOf("_id", "review", "rating"), "product_id = ?", arrayOf(productId.toString()), null, null, null)
        val reviews = mutableListOf<ProductReviews>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val review = cursor.getString(1)
            val rating = cursor.getInt(2)
            reviews.add(ProductReviews(id, productId, review, rating))
        }
        cursor.close()
        return reviews
    }
    fun addReview(productId: Int, review: String, rating: Int){
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("product_id", productId)
        contentValues.put("review", review)
        contentValues.put("rating", rating)
        db.insert("reviews", null, contentValues)
    }
    fun getReviewsTotalRate(productId: Int): Int {
        val db = dbHelper.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.query("reviews", arrayOf("rating"), "product_id = ?", arrayOf(productId.toString()), null, null, null)
            if (cursor.count == 0) {
                // Return a default value if no ratings are found
                return 0
            }
            var rating = 0.0
            var x = 0
            while (cursor.moveToNext()){
                rating += cursor.getDouble(0)
                x++
            }
            if (x > 0){
                rating /= x
            }

            return rating.roundToInt()
        } finally {
            cursor?.close()
        }
    }
}