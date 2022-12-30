package com.sendiko.ternaqu.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sendiko.ternaqu.databinding.CardRvBinding
import com.sendiko.ternaqu.repository.model.Recipe

class RecipeAdapter(
    private val recipe: ArrayList<Recipe>,
    private val context: Context
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
            .load(currentItem.url)
            .fitCenter()
            .into(holder.binding.imageView6)
        holder.binding.textView7.text = currentItem.title
    }

    override fun getItemCount(): Int = recipe.size

}