package com.sendiko.ternaqu.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sendiko.ternaqu.databinding.CardRvBinding
import com.sendiko.ternaqu.repository.model.Product

class ProductAdapter(
    private val product: ArrayList<Product>,
    private val context: Context
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(
        var binding: CardRvBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductViewHolder {
        val binding = CardRvBinding.inflate(LayoutInflater.from(parent.context))
        return ProductAdapter.ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = product[position]
        holder.binding.textView7.text = currentItem.brand
        Glide.with(context)
            .load(currentItem.url)
            .fitCenter()
            .into(holder.binding.imageView6)
    }

    override fun getItemCount(): Int = product.size

}