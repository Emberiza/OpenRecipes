//package com.example.openrecipes.Adapters
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.openrecipes.R
//import com.example.openrecipes.RecipeData
//import kotlinx.android.synthetic.main.itemrow.view.*
//
//
//class Adapter: RecyclerView.Adapter<Adapter.MyViewHolder>(){
//
//    private var myList = emptyList<RecipeData>()
//
//    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.itemrow, parent, false))
//    }
//
//    override fun getItemCount(): Int {
//        return myList.size
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.itemView.titleRow.text = myList[position].title
//
//    }
//
//    fun setData(newList: List<RecipeData>){
//        myList = newList
//        notifyDataSetChanged()
//
//    }
//}