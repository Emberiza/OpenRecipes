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
    val recipes: LiveData<List<RecipeData>?> //list pro vice receptu
        get() = mRecipes
    val recipe: LiveData<RecipeData> //pro jeden recept
        get() = mRecipe
    val timeOut: LiveData<Boolean> //inicializace boolean zda vyprsel cas k ziskani receptu
        get() = mRecipeRequestTimeOut

    private lateinit var mRetrieveRecipesRunnable: RetrieveRecipesRunnable //inicializace funkci
    private lateinit var mRetrieveRecipeRunnable: RetrieveRecipeRunnable

    fun searchRecipesApi(query: String, pageNumber: Int)
    {
        mRetrieveRecipesRunnable = RetrieveRecipesRunnable(query, pageNumber)
        val handler: Future<*> =
            Executor.instance.networkIO().submit(mRetrieveRecipesRunnable)

        // nastavi timeout pro aktualizaci dat
        Executor.instance.networkIO().schedule(
            { //da uzivateli vedet ze byl timeout
                mRecipeRequestTimeOut.postValue(true)
                handler.cancel(true)
            },
            TIMEOUT.toLong(), TimeUnit.MILLISECONDS
        )
    }

    fun searchRecipeByID(recipeID: String) { //vyhleda recept podle jeho ID
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
    //zavolani teto funkce by zrusilo probihajici procesy
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
            //try catch pokud vubec dostaneme odpoved, pokud ne vyhodi error "cannot @GET"
            try {
                val response: Response<RecipeSearchGet> = getRecipes(query, pageNumber).execute()
                //pokud by boolean cancelRequest byl true, funkce se ukonci
                if (cancelRequest) {
                    return
                }
                //pokud response.code() bude 200 (coz HTTP znamena OK), pokracuje se dal, kde vytvorime val list ktery naplnime lifedaty z receptu, pokud ne tak dalsi ERROR
                if (response.code() == 200) {
                    val list = response.body()?.recipes
                    //pokud dostaneme pageNumber 1, vime ze jsme na zacatku a muzeme zaplnit mRecipes celym listem dat
                    if (pageNumber == 1) {
                        mRecipes.postValue(list)
                    } else { //pokud ne, vyuzijeme kotlinskeho also, kterym vytvorime mutableList, ktery opet zaplnime daty z @GET
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

        private fun getRecipes(query: String, pageNumber: Int): Call<RecipeSearchGet> { //zavolama funkci RecipeSearchGet, ktera za pomoci parametry vyhleda query a cislo stranky prevedene na String
            return RetrofitBuild.recipeApiInterface.searchRecipe(
                Constants.API_KEY,
                query, pageNumber.toString()
            )
        }

        fun cancelRequest() { //funkce vypisujici zpravu v pripade zruseni procesu
            Log.d(
                TAG,
                "cancelRequest: canceling the retrieval query"
            )
            cancelRequest = true
        }
    }

    //tato trida je velice podobna predchozi, tentokrat ale bere data jednotlivych receptu a ne listu receptu a tudiz je kratsi a mene narocna, ovsem porad obsahuje osetreni try catch a zjistovani HTTP kodu
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

        //ekvivalent getRecipes(), kde pouze hledame ID receptu
        private fun getRecipe(recipeID: String): Call<RecipeGet> {
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
