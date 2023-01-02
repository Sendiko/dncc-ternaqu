package com.sendiko.ternaqu.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sendiko.ternaqu.databinding.FragmentDashboardBinding
import com.sendiko.ternaqu.repository.ProductRepository
import com.sendiko.ternaqu.repository.ViewModelFactory
import com.sendiko.ternaqu.repository.recipe.RecipeRepository
import com.sendiko.ternaqu.repository.recipe.RecipeViewModel

class DashboardFragment : Fragment() {

    private lateinit var binding : FragmentDashboardBinding

    private fun obtainViewModel(activity: FragmentActivity): RecipeViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this, factory)[RecipeViewModel::class.java]
    }

    private val recipeViewModel by lazy{
        obtainViewModel(requireNotNull(this.activity))
    }

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
        binding.rvProducts.apply {
            layoutManager = horizontalLayoutManager
            adapter = ProductAdapter(ProductRepository().getProduct(), requireContext())
        }

        recipeViewModel.getRecipe().observe(viewLifecycleOwner){
            binding.rvResep.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = RecipeAdapter(it, requireContext())
            }
        }
        
    }

}