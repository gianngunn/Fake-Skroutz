package com.example.giapame

import android.content.ContentValues


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
}