package com.example.openrecipes.Retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openrecipes.Miscellaneous.Constants
import com.example.openrecipes.Miscellaneous.Constants.TIMEOUT
import com.example.openrecipes.Miscellaneous.Executor
import com.example.openrecipes.RecipeData
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

private const val TAG = "RecipeApiClient"

class RecipeApiClient private constructor() {
    private val mRecipes: MutableLiveData<List<RecipeData>?> = MutableLiveData()
    private val mRecipe: MutableLiveData<RecipeData> = MutableLiveData()
    private val mRecipeRequestTimeOut: MutableLiveData<Boolean> = MutableLiveData()
    val recipes: LiveData<List<RecipeData>?>
        get() = mRecipes
    val recipe: LiveData<RecipeData>
        get() = mRecipe
    val timeOut: LiveData<Boolean>
        get() = mRecipeRequestTimeOut

    private lateinit var mRetrieveRecipesRunnable: RetrieveRecipesRunnable
    private lateinit var mRetrieveRecipeRunnable: RetrieveRecipeRunnable

    fun searchRecipesApi(query: String, pageNumber: Int) {
        mRetrieveRecipesRunnable = RetrieveRecipesRunnable(query, pageNumber)
        val handler: Future<*> =
            Executor.instance.networkIO().submit(mRetrieveRecipesRunnable)

        // Set a timeout for the data refresh
        Executor.instance.networkIO().schedule(
            { // let the user know it timed out
                mRecipeRequestTimeOut.postValue(true)
                handler.cancel(true)
            },
            TIMEOUT.toLong(), TimeUnit.MILLISECONDS
        )
    }

    fun searchRecipeByID(recipeID: String) {
        mRetrieveRecipeRunnable = RetrieveRecipeRunnable(recipeID)
        val handler: Future<*> =
            Executor.instance.networkIO().submit(mRetrieveRecipeRunnable)

        mRecipeRequestTimeOut.value = false
        // Set a timeout for the data refresh
        Executor.instance.networkIO().schedule(
            { // let the user know it timed out
                mRecipeRequestTimeOut.value = true
                handler.cancel(true)
            },
            TIMEOUT.toLong(), TimeUnit.MILLISECONDS
        )
    }

    fun cancelRequest() {
        if (this::mRetrieveRecipesRunnable.isInitialized) {
            mRetrieveRecipesRunnable.cancelRequest()
        }

        if (this::mRetrieveRecipeRunnable.isInitialized) {
            mRetrieveRecipeRunnable.cancelRequest()
        }
    }

    private inner class RetrieveRecipesRunnable(
        private val query: String,
        private val pageNumber: Int
    ) :
        Runnable {

        private var cancelRequest = false

        override fun run() {
            try {
                val response: Response<RecipeSearchGet> =
                    getRecipes(query, pageNumber).execute()
                if (cancelRequest) {
                    return
                }
                if (response.code() == 200) {

                    val list = response.body()?.recipes
                    if (pageNumber == 1) {
                        mRecipes.postValue(list)
                    } else {
                        list?.also {
                            val currentRecipes =
                                mRecipes.value?.toMutableList()
                            currentRecipes?.addAll(it)
                            mRecipes.postValue(currentRecipes)
                        }
                    }
                } else {
                    response.errorBody()?.string()?.let { errorString ->
                        Log.e(TAG, "run: error: $errorString")
                    }
                    mRecipes.postValue(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mRecipes.postValue(null)
            }
        }

        private fun getRecipes(
            query: String,
            pageNumber: Int
        ): Call<RecipeSearchGet> {
            return RetrofitBuild.recipeApiInterface.searchRecipe(
                Constants.API_KEY,
                query, pageNumber.toString()
            )
        }

        fun cancelRequest() {
            Log.d(
                TAG,
                "cancelRequest: canceling the retrieval query"
            )
            cancelRequest = true
        }
    }

    private inner class RetrieveRecipeRunnable(
        private val recipeID: String
    ) :
        Runnable {

        private var cancelRequest = false

        override fun run() {
            try {
                val response: Response<RecipeGet> = getRecipe(recipeID).execute()
                if (cancelRequest) {
                    return
                }
                if (response.code() == 200) {

                    val recipe = response.body()?.recipe
                    mRecipe.postValue(recipe)
                } else {
                    response.errorBody()?.string()?.let { errorString ->
                        Log.e(TAG, "run: error: $errorString")
                    }
                    mRecipe.postValue(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mRecipe.postValue(null)
            }
        }

        private fun getRecipes(
            query: String,
            pageNumber: Int
        ): Call<RecipeSearchGet> {
            return RetrofitBuild.recipeApiInterface.searchRecipe(
                Constants.API_KEY,
                query, pageNumber.toString()
            )
        }

        private fun getRecipe(
            recipeID: String
        ): Call<RecipeGet> {
            return RetrofitBuild.recipeApiInterface.getRecipe(
                Constants.API_KEY,
                recipeID
            )
        }

        fun cancelRequest() {
            Log.d(
                TAG,
                "cancelRequest: canceling the retrieval query"
            )
            cancelRequest = true
        }
    }

    companion object {
        val instance by lazy {
            RecipeApiClient()
        }
    }
}
