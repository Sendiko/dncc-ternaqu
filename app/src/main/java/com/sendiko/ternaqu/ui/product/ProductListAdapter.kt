package com.sendiko.ternaqu.ui.product

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sendiko.ternaqu.databinding.CardListItemBinding
import com.sendiko.ternaqu.network.response.ProductItem

class ProductListAdapter(
    private val product: ArrayList<ProductItem>,
    private val context: Context,
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    class ProductListViewHolder(var binCardListItemBinding: CardListItemBinding) :
        RecyclerView.ViewHolder(binCardListItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val binding =
            CardListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val currentItem = product[position]
        holder.binCardListItemBinding.textView15.text = currentItem.title
        holder.binCardListItemBinding.textView17.text = currentItem.description
        holder.binCardListItemBinding.textView25.text = currentItem.price.toString()
        Glide.with(context)
            .load(currentItem.imageUrl)
            .circleCrop()
            .into(holder.binCardListItemBinding.imageView8)
        holder.binCardListItemBinding.root.setOnClickListener {
            onItemClick.onCardRecipeClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return product.size
    }

    interface OnItemClick {
        fun onCardRecipeClick(product: ProductItem)
    }

}