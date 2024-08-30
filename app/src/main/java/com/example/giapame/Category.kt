package com.example.giapame

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class Category : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter
    private lateinit var categorySpinner: Spinner
    private val categories = listOf("TV", "SMARTPHONE", "CONSOLE", "PRINTER")
    //private lateinit var productList : ArrayList<Products>
    // Inflate the layout for this fragment


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)

        recyclerView = view.findViewById(R.id.category_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Create a list of HomeProductViewModel objects
//        val productList = listOf(
//            HomeProductViewModel(R.mipmap.product_tv_1, "Samsung Smart Τηλεόραση 55\" 4K UHD LED UE55CU7172UXXH HDR (2023)", "Price: $394.79"),
//            HomeProductViewModel(R.mipmap.product_tv_2, "LG Smart Τηλεόραση 32\" Full HD LED 32LQ63006LA HDR (2022)", "Price: $203"),
//            //...
//        )
        var productList=Constants.getProductData()
        //productList = ArrayList()
        //productList.add(HomeProductViewModel(R.mipmap.product_tv_1, "Samsung Smart Τηλεόραση 55\" 4K UHD LED UE55CU7172UXXH HDR (2023)", "Price: $394.79"))
        categorySpinner = view.findViewById(R.id.category_spinner)

        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val category = parent?.getItemAtPosition(position) as String
                var selectedCategoryProducts = productList.filter { it.productCategory == category } as ArrayList<HomeProductViewModel>
                adapter = CategoryAdapter(selectedCategoryProducts)
                recyclerView.adapter = adapter
                adapter.onItemClick = { homeProductViewModel ->
                    val fragment = SingleProduct()
                    val bundle = Bundle()
                    bundle.putParcelable("homeProductViewModel", homeProductViewModel)
                    fragment.arguments = bundle
                    val fragmentManager = childFragmentManager
                    adapter.onItemClick = null
                    fragmentManager.beginTransaction()
                        .replace(R.id.categoryFragment, fragment)
                        .addToBackStack("home")
                        .commit()

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
//                ("Not yet implemented")
            }
        }

        return view

    }




}

