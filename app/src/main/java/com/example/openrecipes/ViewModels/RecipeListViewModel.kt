package com.example.openrecipes.ViewModels

import androidx.lifecycle.ViewModel
import com.example.openrecipes.Repositories.RecipeRepository

//viewmodel pro seznam receptu
class RecipeListViewModel : ViewModel() {
    var isViewingRecipes = false
    var isPerformingQuery = false
    private val mRecipeRepository: RecipeRepository by lazy {
        RecipeRepository.instance
    }

    val recipes = mRecipeRepository.recipes

    val isQueryExhausted = mRecipeRepository.isQueryExhausted

    fun searchRecipes(query: String, pageNumber: Int) {
        isViewingRecipes = true
        isPerformingQuery = true
        mRecipeRepository.searchRecipesApi(query, pageNumber)
    }

    fun searchNextPage() {
        if (!isPerformingQuery && isViewingRecipes && !isQueryExhausted.value!!) {
            mRecipeRepository.searchNewPage()
        }
    }

    fun onBackPressed(): Boolean {
        if (isPerformingQuery) {
            isPerformingQuery = false
            mRecipeRepository.cancelRequest()
        }
        return if (isViewingRecipes) {
            isViewingRecipes = false
            false
        } else {
            true
        }
    }
}
