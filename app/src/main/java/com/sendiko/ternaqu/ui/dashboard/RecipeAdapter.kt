package com.sendiko.ternaqu.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sendiko.ternaqu.databinding.CardRvBinding
import com.sendiko.ternaqu.network.response.RecipeItem

class RecipeAdapter(
    private val recipe: ArrayList<RecipeItem>,
    private val context: Context,
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(
        var binding: CardRvBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeViewHolder {
        val binding = CardRvBinding.inflate(LayoutInflater.from(parent.context))
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecipeViewHolder,
        position: Int
    ) {
        val currentItem = recipe[position]
        Glide.with(context)
            .load(currentItem.imageUrl)
            .centerCrop()
            .into(holder.binding.imageView6)
        holder.binding.textView7.text = currentItem.title
        holder.binding.root.setOnClickListener {
            onItemClick.onCardRecipeClick(currentItem)
        }
    }

    override fun getItemCount(): Int = recipe.size

    interface OnItemClick{
        fun onCardRecipeClick(recipe: RecipeItem)
    }

}