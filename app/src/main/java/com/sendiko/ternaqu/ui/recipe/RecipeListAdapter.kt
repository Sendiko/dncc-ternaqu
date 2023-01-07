package com.sendiko.ternaqu.ui.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sendiko.ternaqu.databinding.CardListItemBinding
import com.sendiko.ternaqu.network.response.RecipeItem

class RecipeListAdapter(
    private val recipe: ArrayList<RecipeItem>,
    private val context: Context
) : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>() {

    class RecipeListViewHolder(var binding: CardListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val binding =
            CardListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        val currentItem = recipe[position]
        holder.binding.textView15.text = currentItem.title
        holder.binding.textView17.text = currentItem.benefit
        Glide.with(context)
            .load(currentItem.imageUrl)
            .circleCrop()
            .into(holder.binding.imageView8)
    }

    override fun getItemCount(): Int {
        return recipe.size
    }

}