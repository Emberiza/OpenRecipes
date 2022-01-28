package com.example.openrecipes.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiInterface {
    //potrebne Retrofit @GETy k vyhledavani z listu receptu, a samotne recepty
    @GET("api/search")
    fun searchRecipe(
        @Query("key") key:String,
        @Query("q") query:String,
        @Query("page") page:String,
    ): Call<RecipeSearchGet>
    //zavolaji si pomocne tridy RecipeSearchGet a RecipeGet, ktere jsou ulozene ve svych samostatnych souborech
    @GET("api/get")
    fun getRecipe(
            @Query("key") key:String,
            @Query("rId") rId:String,
    ): Call<RecipeGet>
}