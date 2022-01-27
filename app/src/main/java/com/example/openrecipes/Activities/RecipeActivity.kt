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

class RecipeActivity : AbstractActivity() {
    private lateinit var mRecipeImage: AppCompatImageView
    private lateinit var mRecipeTitle: TextView
    private lateinit var mRecipeRank: TextView
    private lateinit var mRecipeIngredientsContainer: LinearLayout
    private lateinit var mScrollView: ScrollView
    private lateinit var mRecipeViewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        mRecipeImage = findViewById(R.id.recipe_image)
        mRecipeTitle = findViewById(R.id.recipe_title)
        mRecipeRank = findViewById(R.id.recipe_social_score)
        mRecipeIngredientsContainer = findViewById(R.id.ingredients_container)
        mScrollView = findViewById(R.id.parent)

        mRecipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
//        showProgressBar(true)
        subscribeObservers()
        getIncomingIntent()
    }

    private fun getIncomingIntent() {
        if (intent.hasExtra(RECIPE_INTENT)) {
            intent.getParcelableExtra<RecipeData>(
                RECIPE_INTENT
            )?.let {
                mRecipeViewModel.searchRecipeById(it.recipe_id!!)
            }
        }
    }

    private fun subscribeObservers() {
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

    private fun displayErrorScreen(errorMessage: String)  {
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
        showProgressBar(false)
        mRecipeViewModel.mDidRetrieveRecipe = true
    }

    private fun setRecipeProperties(recipe: RecipeData) {
        Glide.with(this)
            .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_launcher_background))
            .load(recipe.image_url)
            .into(mRecipeImage)

        mRecipeTitle.text = recipe.title
        mRecipeRank.text = recipe.social_rank.toString()
        for (ingredients in recipe.ingredients!!) {
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
        showProgressBar(false)
    }

    private fun showParent() {
        mScrollView.visibility = View.VISIBLE
    }

    companion object {
        const val RECIPE_INTENT = "recipe"
    }
}
