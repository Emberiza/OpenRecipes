package com.example.openrecipes.Activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.openrecipes.R
import com.example.openrecipes.RecipeData
import com.example.openrecipes.ViewModels.RecipeViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecipeActivity : AbstractActivity() {
    //inicializace promennych
    private lateinit var mRecipeImage: AppCompatImageView
    private lateinit var mRecipeTitle: TextView
    private lateinit var mRecipeRank: TextView
    private lateinit var mRecipeIngredientsContainer: LinearLayout
    private lateinit var mScrollView: ScrollView
    private lateinit var mRecipeViewModel: RecipeViewModel
    private lateinit var mFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        //pripojeni promennych na komponenty layoutu activity_recipe
        mRecipeImage = findViewById(R.id.recipe_image)

        mRecipeTitle = findViewById(R.id.recipe_title)

//        mRecipeRank = findViewById(R.id.recipe_social_score)

        mRecipeIngredientsContainer = findViewById(R.id.ingredients_container)
        mScrollView = findViewById(R.id.parent)
        //inicializace ViewModelu ze souboru RecipeViewModel
        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        subscribeObservers()
        getIncomingIntent()

    }
    //zjisti ktery recept chceme zobrazit
    private fun getIncomingIntent() {
        if (intent.hasExtra(RECIPE_INTENT)) {
            intent.getParcelableExtra<RecipeData>(
                RECIPE_INTENT
            )?.let {
                mRecipeViewModel.searchRecipeById(it.recipe_id!!)
            }
        }
    }

    private fun subscribeObservers() //Observer zjistuje dostupnost kliknuteho receptu, pokud vse sedi zacne setRecipeProperties(), vyprsi cas na API Query Request (tudiz chyba typu recept neexistuje/API nefunkcni) zacne displayErrorScreen()
    {
        mRecipeViewModel.recipes.observe(
            this,
            Observer { recipe ->
                recipe?.let {
                    if (recipe.recipe_id.equals(mRecipeViewModel.recipeId)) {
                        setRecipeProperties(it)
                        mRecipeViewModel.mDidRetrieveRecipe = true
                    }
                }
            }
        )

        mRecipeViewModel.isRecipeRequestTimeOut.observe(
            this,
            Observer {
                if (it && !mRecipeViewModel.mDidRetrieveRecipe) {
                    displayErrorScreen(getString(R.string.error_retrieve_message))
                }
            }
        )
    }

    private fun displayErrorScreen(errorMessage: String)  //v pripade ze Observer zjisti, ze nelze ziskat recept, vyhodi chybovou hlasku
    {
        mRecipeTitle.text = getString(R.string.error_retrieve_message)
        mRecipeRank.text = ""
        TextView(this).apply {
            if (!TextUtils.isEmpty(errorMessage)) {
                text = errorMessage
            } else {
                text = getString(R.string.error_retrieve_message)
            }
            textSize = 15F
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }.also {
            mRecipeIngredientsContainer.addView(it)
        }

        Glide.with(this)
            .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_launcher_background))
            .load(R.drawable.ic_launcher_background)
            .into(mRecipeImage)

        showParent()
        mRecipeViewModel.mDidRetrieveRecipe = true
    }
    //zobrazi veskere atributy vybraneho receptu
    private fun setRecipeProperties(recipe: RecipeData) {
        Glide.with(this)  //pomoci Glide ziskame obrazek receptu
            .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_launcher_background))
            .load(recipe.image_url)
            .into(mRecipeImage)

        mRecipeTitle.text = recipe.title    //ziskame jmeno receptu
//        mRecipeRank.text = recipe.social_rank.toString()
        for (ingredients in recipe.ingredients!!)   //pro kazdou ingredienci se ji zaplni radek
        {
            TextView(this).apply {
                text = ingredients
                textSize = 15F
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }.also {
                mRecipeIngredientsContainer.addView(it)
            }
        }

        showParent()
    }

    private fun showParent() { //defaultne je hodnota visibility=gone (hodnota 2, kompletne schovana), v pripade inicializace se prepne na visible (0)
        mScrollView.visibility = View.VISIBLE
    }

    companion object { //deklarace konstanty
        const val RECIPE_INTENT = "recipe"
    }


}
