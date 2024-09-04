package com.example.giapame

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class Cart : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var reviewDao: ReviewDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        recyclerView = view.findViewById(R.id.cart_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val cartList = Constants.getCartItems()
        reviewDao = ReviewDao(ReviewDataBaseHelper(requireContext()))
        adapter = CartAdapter(cartList, reviewDao)
        recyclerView.adapter = adapter

        adapter.onItemClick = { homeProductViewModel ->
            val fragment = SingleProduct()
            val bundle = Bundle()
            bundle.putParcelable("homeProductViewModel", homeProductViewModel)
            fragment.arguments = bundle
            val fragmentManager = childFragmentManager
            adapter.onItemClick = null
            fragmentManager.beginTransaction()
                .replace(R.id.cartFragment, fragment)
                .addToBackStack("favorite")
                .commit()

        }
        var totalPrice = 0.0
        for (item in cartList) {
            totalPrice += item.productPrice.toFloat()
        }
        val promoBtn = view.findViewById<Button>(R.id.btn_apply_promo)
        var promoCode: String
        var key = 1
        promoBtn.setOnClickListener {
            val txtCodeInput = view.findViewById<EditText>(R.id.enter_promo_code)
            promoCode = txtCodeInput.text.toString()
            if(key == 0){
                Toast.makeText(context, getString(R.string.promoCodeUsed), Toast.LENGTH_SHORT).show()
            }
            if(promoCode == "Fake Skroutz" && key == 1){
                totalPrice -= (totalPrice * 0.15)
                key = 0
                Toast.makeText(context, getString(R.string.promoCodeCorrect), Toast.LENGTH_SHORT).show()
                //Snackbar.make(view, "Ο κωδικός επιβεβαιώθηκε!", Snackbar.LENGTH_SHORT).show()
            }
            val tp = view.findViewById<TextView>(R.id.totalPriceHolder)
            tp.text = "%.2f".format(totalPrice)
        }

        val tp = view.findViewById<TextView>(R.id.totalPriceHolder)
        tp.text = "%.2f".format(totalPrice)

        return view
    }
}