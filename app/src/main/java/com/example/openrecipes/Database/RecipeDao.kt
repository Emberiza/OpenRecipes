package com.example.openrecipes.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.openrecipes.RecipeData

//toto byl pokus o Room database DAO soubor

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecipes(recipes: List<RecipeData>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeData): Long



}