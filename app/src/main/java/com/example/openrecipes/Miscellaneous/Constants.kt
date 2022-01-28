package com.example.openrecipes.Miscellaneous

import android.net.Uri

//seznam konstant, DEFAULT_SEARCH a DEFAULT_SEARCH_IMAGES jsou pouzivany pro kategorie jidel, protoze je jich malo statickych

object Constants {

        const val API_URL = "https://recipesapi.herokuapp.com"
        const val API_KEY = ""
        const val TIMEOUT = "5000"
        val DEFAULT_SEARCH = arrayOf("Barbecue", "Breakfast", "Chicken", "Beef", "Brunch", "Dinner", "Wine", "Italian");
        val DEFAULT_SEARCH_IMAGES  = arrayOf("barbecue", "breakfast", "chicken", "beef", "brunch", "dinner", "wine", "italian");


}
