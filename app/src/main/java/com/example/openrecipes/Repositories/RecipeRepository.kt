package com.example.openrecipes.Repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.openrecipes.RecipeData
import com.example.openrecipes.Retrofit.RecipeApiClient


class RecipeRepository private constructor() {
    private val recipeClient by lazy { RecipeApiClient.instance }
    lateinit var mQuery: String
    var mPageNumber: Int = 0
    private val mIsQueryExhausted: MutableLiveData<Boolean> = MutableLiveData()
    private val mRecipes: MediatorLiveData<List<RecipeData>> = MediatorLiveData()

    init {
        initMediators()
    }

    val recipes: LiveData<List<RecipeData>?> //list receptu
        get() = mRecipes

    val recipe: LiveData<RecipeData> //jeden vybrany recept
        get() = recipeClient.recipe

    val isRecipeRequestTimeOut: LiveData<Boolean> //boolean zda vyprsel cas na API query request
        get() = recipeClient.timeOut

    val isQueryExhausted: LiveData<Boolean> //boolean pokud dosly data ke zpracovani
        get() = mIsQueryExhausted

    fun searchRecipeById(recipeID: String) { //vyhledani podle ID
        recipeClient.searchRecipeByID(recipeID)
    }

    fun searchRecipesApi(query: String, pageNumber: Int) {
        var number = pageNumber
        if (number == 0) {
            number = 1
        }
        mQuery = query
        mPageNumber = pageNumber
        mIsQueryExhausted.value = false
        recipeClient.searchRecipesApi(query, number)
    }

    fun searchNewPage() {
        searchRecipesApi(mQuery, mPageNumber + 1)
    }

    fun cancelRequest() {
        recipeClient.cancelRequest()
    }

    private fun initMediators() {
        val recipeListApiSource = recipeClient.recipes
        mRecipes.addSource(recipeListApiSource) { recipes ->
            recipes?.let {
                mRecipes.value = it
                doneQuery(it)
            } ?: run {
                // search database cache
                doneQuery(null)
            }
        }
    }

    private fun doneQuery(list: List<RecipeData>?) {
        mIsQueryExhausted.value = list == null || list.size % 30 != 0
    }

    companion object {
        val instance: RecipeRepository by lazy {
            RecipeRepository()
        }
    }
}