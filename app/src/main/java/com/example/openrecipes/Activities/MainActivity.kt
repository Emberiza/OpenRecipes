//package com.example.openrecipes.Activities
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.FrameLayout
//import android.widget.ProgressBar
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.core.content.ContextCompat.startActivity
//import com.example.openrecipes.R
//
//
//class MainActivity : AppCompatActivity() { //jednoduse prehodi aktivitu na ChoiceActivity
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_base)
//        switchToAbstract()
//    }
//    private fun switchToAbstract (){
//        val intent = Intent(this, ChoiceActivity::class.java)
//        startActivity(intent)
//    }
//}