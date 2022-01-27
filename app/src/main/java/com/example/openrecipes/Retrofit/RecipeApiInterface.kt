package com.example.openrecipes.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiInterface {

    @GET("api/search")
    fun searchRecipe(
        @Query("key") key:String,
        @Query("q") query:String,
        @Query("page") page:String,
    ): Call<RecipeSearchGet>

    @GET("api/get")
    fun getRecipe(
            @Query("key") key:String,
            @Query("rId") rId:String,
    ): Call<RecipeGet>
}