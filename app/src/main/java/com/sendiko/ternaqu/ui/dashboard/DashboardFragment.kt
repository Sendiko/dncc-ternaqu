package com.sendiko.ternaqu.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentDashboardBinding
import com.sendiko.ternaqu.repository.ProductRepository
import com.sendiko.ternaqu.repository.RecipeRepository

class DashboardFragment : Fragment() {

    private lateinit var binding : FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val horizontalLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvResep.apply {
            layoutManager = horizontalLayoutManager
            adapter = RecipeAdapter(RecipeRepository().getRecipeList(), requireContext())
        }

        val alsoHorizontalLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvProducts.apply {
            layoutManager = alsoHorizontalLayoutManager
            adapter = ProductAdapter(ProductRepository().getProduct(), requireContext())
        }
        
        
    }

}