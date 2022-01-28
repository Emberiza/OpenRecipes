package com.example.openrecipes.Retrofit

import com.example.openrecipes.Miscellaneous.Constants.API_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuild { //doporuceny zpusob implementace RecipeApiInterface z Gson dat ziskanych z API
    val recipeApiInterface: RecipeApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeApiInterface::class.java)
    }
}