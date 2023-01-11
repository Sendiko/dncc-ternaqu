package com.sendiko.ternaqu.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sendiko.ternaqu.databinding.CardRvBinding
import com.sendiko.ternaqu.network.response.ProductItem

class ProductAdapter(
    private val product: ArrayList<ProductItem>,
    private val context: Context,
    private val onItemClick : OnItemClick
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(
        var binding: CardRvBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = CardRvBinding.inflate(LayoutInflater.from(parent.context))
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = product[position]
        holder.binding.textView7.text = currentItem.title
        Glide.with(context)
            .load(currentItem.imageUrl)
            .centerCrop()
            .into(holder.binding.imageView6)
        holder.binding.root.setOnClickListener {
            onItemClick.onCardRecipeClick(currentItem)
        }
    }

    override fun getItemCount(): Int = product.size

    interface OnItemClick {
        fun onCardRecipeClick(product: ProductItem)
    }

}