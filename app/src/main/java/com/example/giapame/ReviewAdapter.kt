package com.example.giapame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.RatingBar
import android.widget.TextView
import java.security.AccessController.getContext


class ReviewAdapter(private val reviews: MutableList<ProductReviews>) : BaseAdapter() {

    override fun getCount(): Int {
        return reviews.size
    }

    override fun getItem(position: Int): Any {
        return reviews[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)

        val review = reviews[position]

        val reviewText = view.findViewById<TextView>(R.id.review_text)
        reviewText.text = review.review

        val rating = view.findViewById<TextView>(R.id.rating)

        rating.text = review.rating.toString() + "/5"

        return view
    }
    fun updateReviews(newReviews: List<ProductReviews>){
        reviews.clear()
        reviews.addAll(newReviews)
        notifyDataSetChanged()
    }
}