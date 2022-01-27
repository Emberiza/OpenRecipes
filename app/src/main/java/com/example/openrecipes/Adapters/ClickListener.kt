package com.example.openrecipes.Adapters

interface ClickListener {
    fun onRecipeClick(position: Int)
    fun onCategoryClick(category: String)
}