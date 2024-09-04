package com.example.giapame

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class CategoryAdapter(private val productList: ArrayList<HomeProductViewModel>, private val reviewDao: ReviewDao) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    //ArrayList
    var onItemClick : ((HomeProductViewModel) -> Unit)? = null
    //create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflates the productslist
        //that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.productslist, parent, false)
        return ViewHolder(view)
    }



    //binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val homeProductViewModel = productList[position]

        //sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(homeProductViewModel.image)

        //sets the text to the textview from our itemHolder class
        holder.textView1.setText(homeProductViewModel.productTitle)

        //sets the text to the textview from our itemHolder class
        holder.textView2.text = homeProductViewModel.productPrice

        var ratingNumber = reviewDao.getReviewsTotalRate(homeProductViewModel.productTitle)
        if (ratingNumber == 0){
            holder.textView3.text = "N/A"
        }else{
            holder.textView3.text = ratingNumber.toString()
        }

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(homeProductViewModel)
        }

    }

    //return the number of the items in the list
    override fun getItemCount(): Int {
        return productList.size
    }

    //Holds the views for adding it to image and text
    class ViewHolder(homeProductView: View) : RecyclerView.ViewHolder(homeProductView){
        val imageView: ImageView = itemView.findViewById(R.id.productIcon)
        val textView1: TextView = itemView.findViewById(R.id.productTitle)
        val textView2: TextView = itemView.findViewById(R.id.productPrice)
        val textView3: TextView = itemView.findViewById(R.id.productRating)
    }

}