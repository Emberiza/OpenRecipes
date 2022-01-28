package com.example.openrecipes

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//data ktera chceme ziskat z API jsou parcelizovana, pro dalsi procesy v aktivitach. ne vsechny jsem vyuzil, ovsem radeji jsem je vypsal vsechny

@Parcelize
@Entity(tableName = "recipe_table")
data class RecipeData(
    @SerializedName("title") @Expose var title: String? = null,
    @SerializedName("publisher") @Expose val publisher: String? = null,
    @SerializedName("ingredients") @Expose val ingredients: Array<String>? = null,
    @SerializedName("recipe_id") @Expose val recipe_id: String? = null,
    @SerializedName("image_url") @Expose val image_url: String? = null,
    @SerializedName("social_rank") @Expose val social_rank: Float? = null
) : Parcelable