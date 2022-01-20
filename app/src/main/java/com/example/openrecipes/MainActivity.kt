package com.example.openrecipes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

private val Adapter by lazy {Adapter()}

class MainActivity : AppCompatActivity() {
    lateinit var search : Button
    lateinit var saved : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search = findViewById(R.id.searchRecipesButton)
        search.setOnClickListener() {
            changeToSearch(it)
        }
//        saved = findViewById(R.id.savedRecipesButton)
//        saved.setOnClickListener() {
//            changeToSaved(it)
//        }










    }

//    private fun changeToSaved(it: View?) {
//        val intent = Intent(this, SavedActivity::class.java)
//        startActivity(intent)
//    }

    private fun changeToSearch(button: View) {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

}













//val retrofit=Retrofit.Builder()
//    .addConverterFactory(GsonConverterFactory.create())
//    .baseUrl("https://recipesapi.herokuapp.com")
//    .build()
//val RetrofitGet = retrofit.create(com.example.openrecipes.RetrofitGet::class.java)
//val mcall: Call<List>