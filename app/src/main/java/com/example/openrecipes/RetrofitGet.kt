package com.example.openrecipes

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitGet {

    //search
    @GET("api/search")
    suspend fun search(
        @Query("key") key : String,
        @Query("q") query : String,
        @Query("page") page : String
    );

    @GET("get")
    suspend fun get(
        @Query("key") key : String,
        @Query("rId") recipe_id : String
    );
}