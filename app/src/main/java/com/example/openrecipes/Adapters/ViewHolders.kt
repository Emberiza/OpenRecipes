package com.example.openrecipes.Adapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.openrecipes.R
import de.hdodenhof.circleimageview.CircleImageView

class ViewHolders {
}
class CategoryViewHolder(itemView: View, private val mOnRecipeListener: ClickListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var categoryImage: CircleImageView = itemView.findViewById(R.id.category_image)
    var categoryTitle: TextView = itemView.findViewById(R.id.category_title)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        mOnRecipeListener.onCategoryClick(categoryTitle.text.toString())
    }
}

class LoadingViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView)

class RecipeViewHolder(itemView: View, private val onRecipeListener: ClickListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var title: TextView = itemView.findViewById(R.id.recipe_title)
    var publisher: TextView = itemView.findViewById(R.id.recipe_publisher)
    var socialScore: TextView = itemView.findViewById(R.id.recipe_social_score) //zvlastni api hodnota, ktera mi neprisla potrebna
    var image: AppCompatImageView = itemView.findViewById(R.id.recipe_image)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        onRecipeListener.onRecipeClick(adapterPosition)
    }
}

class SearchExhaustedViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView)
