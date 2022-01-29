package com.example.openrecipes.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import com.example.openrecipes.R


class MainActivity : AppCompatActivity() { //ukaze na 3 vteriny custom splashscreen a prehodi aktivitu na pruchod receptu z API
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            switchToRecipes()
        },3000)
    }
    private fun switchToRecipes (){
        val intent = Intent(this, RecipeListActivity::class.java)
        startActivity(intent)
    }
}