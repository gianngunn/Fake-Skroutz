package com.example.giapame

object Constants {
    private val favoriteList = ArrayList<HomeProductViewModel>()
    private val cartList = ArrayList<HomeProductViewModel>()
    fun getProductData():ArrayList<HomeProductViewModel>{

        val productList=ArrayList<HomeProductViewModel>()

        val pro1=HomeProductViewModel(R.mipmap.product_tv_1,
            R.string.productTitle1,
            "394.79",
            "Samsung",
            R.string.productDesc1,
            "Μαύρο",
            4,
            "55",
            "TV"
            )
        productList.add(pro1)

        val pro2=HomeProductViewModel(R.mipmap.product_tv_2,
            R.string.productTitle2,
            "203",
            "LG",
            R.string.productDesc2,
            "Μαύρο",
            3,
            "32",
            "TV"
            )
        productList.add(pro2)

        val pro3=HomeProductViewModel(R.mipmap.product_sp_1,
            R.string.productTitle3,
            "129.90",
            "Xiaomi",
            R.string.productDesc3,
            "Μπλε",
            0,
            "6.74",
            "SMARTPHONE"
            )
        productList.add(pro3)

        val pro4=HomeProductViewModel(R.mipmap.product_sp_2,
            R.string.productTitle4,
            "798",
            "Apple",
            R.string.productDesc4,
            "Μαύρο",
            10,
            "6.1",
            "SMARTPHONE")
        productList.add(pro4)

        val pro5=HomeProductViewModel(R.mipmap.product_con_1,
            R.string.productTitle5,
            "545.9",
            "Sony",
             R.string.productDesc5,
            "Λευκό",
            8,
            "358 × 96 × 216mm",
            "CONSOLE")
        productList.add(pro5)

        val pro6=HomeProductViewModel(R.mipmap.product_pr_1,
            R.string.productTitle6,
            "115",
            "Canon",
            R.string.productDesc6,
            "Μαύρο",
            12,
            "372 x 365 x 158mm",
            "PRINTER")
        productList.add(pro6)

        return productList
    }

    fun getFavoriteData(): ArrayList<HomeProductViewModel>{
        return favoriteList
    }

    fun addProductToFavorites(product: HomeProductViewModel){
        favoriteList.add(product)
    }

    fun removeProductFromFavorites(product: HomeProductViewModel){
        favoriteList.remove(product)
    }

    fun getCartItems(): ArrayList<HomeProductViewModel>{
        return cartList
    }

    fun addProductToCart(product: HomeProductViewModel){
        cartList.add(product)

    }

    fun removeProductFromCart(product: HomeProductViewModel){
        cartList.remove(product)
    }
}