package com.example.openrecipes.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.openrecipes.R

class ChoiceActivity : AppCompatActivity() {
    lateinit var search: Button
    lateinit var saved: Button
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


//        val frameLayout: FrameLayout = constraintLayout.findViewById(R.id.activity_content)
//        layoutInflater.inflate(layoutResID, frameLayout, true)
//        super.setContentView(constraintLayout)
    }

    private fun changeToSearch(button: View?) {
        val intent = Intent(this, RecipeListActivity::class.java)
        startActivity(intent)
    }
//        saved = findViewById(R.id.savedRecipesButton)
//        saved.setOnClickListener() {
///            changeToSaved(it)
//  }
}