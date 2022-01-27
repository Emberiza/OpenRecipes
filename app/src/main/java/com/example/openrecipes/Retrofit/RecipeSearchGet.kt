package com.example.openrecipes.Retrofit;

import com.example.openrecipes.RecipeData
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

data class RecipeSearchGet(
    @SerializedName("count") @Expose val count: Int,
    @SerializedName("recipes") @Expose val recipes: List<RecipeData>
) {
    override fun toString(): String {
        return "RecipeSearchGet{count=$count, recipes=$recipes}"
    }
}
