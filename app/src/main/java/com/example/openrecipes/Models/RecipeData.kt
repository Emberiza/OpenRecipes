package com.example.openrecipes

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//data ktera chceme ziskat z API jsou parcelizovana, pro dalsi procesy v aktivitach. ne vsechny jsem vyuzil, ovsem radeji jsem je vypsal vsechny
//Entity a Primary key byly pridany ve snaze vytvorit Room database caching

@Parcelize
@Entity(tableName = "RECIPE")
data class RecipeData(
    @PrimaryKey
    @SerializedName("recipe_id") @Expose val recipe_id: String? = null,
    @SerializedName("title") @Expose var title: String? = null,
    @SerializedName("publisher") @Expose val publisher: String? = null,
    @SerializedName("ingredients") @Expose val ingredients: Array<String>? = null,
    @SerializedName("image_url") @Expose val image_url: String? = null,
    @SerializedName("social_rank") @Expose val social_rank: Float? = null
) : Parcelable {
    //automaticky vygenerovany kod, ktery byl nabidnut android studiem
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecipeData

        if (recipe_id != other.recipe_id) return false
        if (title != other.title) return false
        if (publisher != other.publisher) return false
        if (ingredients != null) {
            if (other.ingredients == null) return false
            if (!ingredients.contentEquals(other.ingredients)) return false
        } else if (other.ingredients != null) return false
        if (image_url != other.image_url) return false
        if (social_rank != other.social_rank) return false

        return true
    }

    override fun hashCode(): Int {
        var result = recipe_id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (publisher?.hashCode() ?: 0)
        result = 31 * result + (ingredients?.contentHashCode() ?: 0)
        result = 31 * result + (image_url?.hashCode() ?: 0)
        result = 31 * result + (social_rank?.hashCode() ?: 0)
        return result
    }
}
