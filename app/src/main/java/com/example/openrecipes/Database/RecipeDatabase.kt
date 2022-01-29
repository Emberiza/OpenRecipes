package com.example.openrecipes.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.openrecipes.RecipeData

//toto byl pokus o Room databazi

@Database(entities = [RecipeData::class], version = 1)
@TypeConverters(RecipeConverter::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun getRecipeDao() : RecipeDao

    companion object {
        const val DATABASE_NAME = "recipes_db"
        private lateinit var instance: RecipeDatabase
        fun getInstance(context: Context): RecipeDatabase =
            if (this::instance.isInitialized) instance else {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    DATABASE_NAME
                ).build()
                instance
            }
    }
}