package com.example.giapame

import android.content.res.ColorStateList
import android.content.res.Resources
import android.media.Rating
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.findViewTreeViewModelStoreOwner


class SingleProduct : Fragment() {

    private lateinit var reviewDao: ReviewDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Initialize reviewDao
        reviewDao = ReviewDao(ReviewDataBaseHelper(requireContext()))

        val view = inflater.inflate(R.layout.fragment_single_product, container, false)

        val homeProductViewModel = arguments?.getParcelable<HomeProductViewModel>("homeProductViewModel")


        if (homeProductViewModel != null) {
            val title = view.findViewById<TextView>(R.id.singleProductTitle)
            val image = view.findViewById<ImageView>(R.id.singleProductImage)
            val price = view.findViewById<TextView>(R.id.singleProductPriceView)
            val description = view.findViewById<TextView>(R.id.singleProductDescriptionView)
            val favoriteButton = view.findViewById<ImageButton>(R.id.add_to_favorite_button)
            val cartButton = view.findViewById<ImageButton>(R.id.add_remove_cart_item)




            title.text = resources.getString(homeProductViewModel.productTitle)
            image.setImageResource(homeProductViewModel.image)
            price.text = homeProductViewModel.productPrice
            description.text = resources.getString(homeProductViewModel.productDescription)

            val favoriteProducts = Constants.getFavoriteData()
            val cartItems = Constants.getCartItems()

            if (favoriteProducts.any { it.productTitle == homeProductViewModel.productTitle }){
                favoriteButton.imageTintList = ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.red, null))
            } else {
                favoriteButton.imageTintList = ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.black, null))
            }

            if (cartItems.any{it.productTitle == homeProductViewModel.productTitle}){
                cartButton.setImageResource(R.drawable.remove_from_cart)
            } else {
                cartButton.setImageResource(R.drawable.add_to_cart)
            }

            favoriteButton.setOnClickListener{
                if (favoriteProducts.any{it.productTitle == homeProductViewModel.productTitle}){
                    //remove favorite
                    Constants.removeProductFromFavorites(homeProductViewModel)
                    favoriteButton.imageTintList = ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.black, null))
                } else {
                    //add favorite
                    Constants.addProductToFavorites(homeProductViewModel)
                    favoriteButton.imageTintList = ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.red, null))
                }

            }

            cartButton.setOnClickListener{
                if (cartItems.any { it.productTitle == homeProductViewModel.productTitle }) {
                    Constants.removeProductFromCart(homeProductViewModel)
                    cartButton.setImageResource(R.drawable.add_to_cart)
                } else {
                    Constants.addProductToCart(homeProductViewModel)
                    cartButton.setImageResource(R.drawable.remove_from_cart)
                }

            }

            val reviewEditText = view.findViewById<EditText>(R.id.review_edit_text)
            val ratingBar = view.findViewById<RatingBar>(R.id.rating_bar)
            val submitReviewButton = view.findViewById<Button>(R.id.submit_review_button)
            val reviewsListView = view.findViewById<ListView>(R.id.reviews_list_view)
            // Retrieve reviews
            val reviews = reviewDao.getReviewsForProduct(homeProductViewModel.productTitle)
            val reviewAdapter = ReviewAdapter(reviews)
            reviewsListView.adapter = reviewAdapter

            submitReviewButton.setOnClickListener{
                val reviewText = reviewEditText.text.toString()
                val rating = ratingBar.rating.toInt()
                reviewDao.addReview(homeProductViewModel.productTitle, reviewText, rating)
                reviewEditText.setText("")
                ratingBar.rating = 0f

                val newReviews = reviewDao.getReviewsForProduct(homeProductViewModel.productTitle)
                reviewAdapter.updateReviews(newReviews)
            }
        }


        return view
    }

}