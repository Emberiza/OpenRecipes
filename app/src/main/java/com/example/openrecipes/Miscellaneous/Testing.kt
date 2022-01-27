package com.example.openrecipes.Miscellaneous

import android.util.Log
import com.example.openrecipes.RecipeData


object Testing {
    fun printRecipes(tag: String?, list: List<RecipeData>) {
        for (recipe in list) {
            Log.d(
                tag,
                "printRecipes: ${recipe.recipe_id}, ${recipe.title}"
            )
        }
    }
}
