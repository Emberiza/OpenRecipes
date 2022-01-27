package com.example.openrecipes.Retrofit;

import com.example.openrecipes.RecipeData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipeGet(
    @SerializedName("recipe") @Expose val recipe: RecipeData
) {
    override fun toString(): String {
        return "RecipeGet{recipe=$recipe}"
    }
}
