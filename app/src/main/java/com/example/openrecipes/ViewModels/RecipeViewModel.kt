package com.example.openrecipes.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openrecipes.RecipeData
import com.example.openrecipes.Repositories.RecipeRepository
import kotlinx.coroutines.launch
import com.example.openrecipes.Database.RecipeDao

//viewmodel pro jednotlive recepty
class RecipeViewModel : ViewModel() {
    private val mRecipeRepository: RecipeRepository by lazy {
        RecipeRepository.instance
    }

    lateinit var recipeId: String

    var mDidRetrieveRecipe: Boolean = false

    val recipes = mRecipeRepository.recipe

    val isRecipeRequestTimeOut = mRecipeRepository.isRecipeRequestTimeOut

    fun searchRecipeById(recipeId: String) {
        this.recipeId = recipeId
        mRecipeRepository.searchRecipeById(recipeId)
    }

}
