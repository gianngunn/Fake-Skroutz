package com.example.giapame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class Home : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeAdapter
    private lateinit var searchView: SearchView
    private lateinit var reviewDao: ReviewDao
    //private lateinit var productList : ArrayList<Products>
    // Inflate the layout for this fragment


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        reviewDao = ReviewDao(ReviewDataBaseHelper(requireContext()))
        recyclerView = view.findViewById(R.id.home_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        searchView = view.findViewById(R.id.product_search_view)


        // Create a list of HomeProductViewModel objects
//        val productList = listOf(
//            HomeProductViewModel(R.mipmap.product_tv_1, "Samsung Smart Τηλεόραση 55\" 4K UHD LED UE55CU7172UXXH HDR (2023)", "Price: $394.79"),
//            HomeProductViewModel(R.mipmap.product_tv_2, "LG Smart Τηλεόραση 32\" Full HD LED 32LQ63006LA HDR (2022)", "Price: $203"),
//            //...
//        )
        val productList=Constants.getProductData()
        //productList = ArrayList()
        //productList.add(HomeProductViewModel(R.mipmap.product_tv_1, "Samsung Smart Τηλεόραση 55\" 4K UHD LED UE55CU7172UXXH HDR (2023)", "Price: $394.79"))
        reviewDao = ReviewDao(ReviewDataBaseHelper(requireContext()))
        adapter = HomeAdapter(productList, reviewDao)

        recyclerView.adapter = adapter

        adapter.onItemClick = { homeProductViewModel ->
            val fragment = SingleProduct()
            val bundle = Bundle()
            bundle.putParcelable("homeProductViewModel", homeProductViewModel)
            fragment.arguments = bundle
            val fragmentManager = childFragmentManager
            adapter.onItemClick = null
            fragmentManager.beginTransaction()
                .replace(R.id.homeFragment, fragment)
                .addToBackStack("home")
                .commit()

        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission
                searchProducts(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    // Clear the search query, show all products
                    searchProducts(null)
                }
                return true
            }
        })

            return view

    }
    private fun searchProducts(query: String?) {

        var productList = Constants.getProductData()
        if (!query.isNullOrEmpty()) {

                val filteredList = productList.filter {
                    val proTitleResId = it.productTitle
                    val proTitleString = resources.getString(proTitleResId)

                   proTitleString.contains(query, ignoreCase = true) }.toMutableList() as ArrayList<HomeProductViewModel>
                adapter.updateList(filteredList)
            }
        else{
            productList = Constants.getProductData()
             adapter.updateList(productList)
        }
        adapter.notifyDataSetChanged()
    }

}

