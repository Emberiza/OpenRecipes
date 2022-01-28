package com.example.openrecipes.Adapters

interface ClickListener {   //hlida kliknuti na kategorie nebo recepty
    fun onRecipeClick(position: Int)
    fun onCategoryClick(category: String)
}