package com.example.openrecipes

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.SerializedName
import org.w3c.dom.Text
import java.security.AccessControlContext


class SearchActivity : AppCompatActivity() {
    lateinit var linearLayout : LinearLayout

    lateinit var topLayout : LinearLayout
    lateinit var searchBar : EditText
    lateinit var searchButton : Button
    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

    fun setupRecyclerview() {
        recyclerView.adapter = Adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}


