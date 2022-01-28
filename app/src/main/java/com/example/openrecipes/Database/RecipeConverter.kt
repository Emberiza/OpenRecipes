//package com.example.openrecipes.Database
//
//import androidx.room.TypeConverter
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//
//object RecipeConverter {
//
//    @TypeConverter
//    fun fromString(value: String?): Array<String> {
//        val listType = object : TypeToken<Array<String?>?>() {}.type
//        return Gson().fromJson(value, listType)
//    }
//
//    @TypeConverter
//    fun fromArrayList(list: Array<String?>?): String = Gson().toJson(list)
//}