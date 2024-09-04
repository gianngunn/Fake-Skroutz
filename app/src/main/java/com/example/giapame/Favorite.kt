package com.example.giapame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Favorite : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdapter
    private lateinit var noFavoritesTextView: TextView
    private lateinit var reviewDao: ReviewDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        recyclerView = view.findViewById(R.id.favorite_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        noFavoritesTextView = view.findViewById(R.id.no_favorites_text_view)

        val favoriteList=Constants.getFavoriteData()
        reviewDao = ReviewDao(ReviewDataBaseHelper(requireContext()))
        adapter = FavoriteAdapter(favoriteList, reviewDao)
        recyclerView.adapter = adapter
        if (favoriteList.isEmpty()) {
            recyclerView.visibility = View.GONE
            noFavoritesTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            noFavoritesTextView.visibility = View.GONE
        }


        adapter.onItemClick = { homeProductViewModel ->
            val fragment = SingleProduct()
            val bundle = Bundle()
            bundle.putParcelable("homeProductViewModel", homeProductViewModel)
            fragment.arguments = bundle
            val fragmentManager = childFragmentManager
            adapter.onItemClick = null
            fragmentManager.beginTransaction()
                .replace(R.id.favoriteFragment, fragment)
                .addToBackStack("favorite")
                .commit()

        }


        return view
    }



}