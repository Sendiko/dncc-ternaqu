package com.sendiko.ternaqu.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentDashboardBinding
import com.sendiko.ternaqu.repository.helper.ViewModelFactory
import com.sendiko.ternaqu.repository.product.ProductViewModel
import com.sendiko.ternaqu.repository.recipe.RecipeViewModel

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    private fun obtainRecipeViewModel(activity: FragmentActivity): RecipeViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this, factory)[RecipeViewModel::class.java]
    }

    private fun obtainProductViewModel(activity: FragmentActivity): ProductViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this, factory)[ProductViewModel::class.java]
    }

    private val recipeViewModel by lazy {
        obtainRecipeViewModel(requireNotNull(this.activity))
    }

    private val productViewModel by lazy {
        obtainProductViewModel(requireNotNull(this.activity))
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

        binding.buttonJoinForum.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_forumFragment)
        }

        productViewModel.getProduct().observe(viewLifecycleOwner) {
            binding.rvProducts.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = ProductAdapter(it, requireContext())
            }
        }

        recipeViewModel.getRecipe().observe(viewLifecycleOwner) {
            binding.rvResep.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = RecipeAdapter(it, requireContext())
            }
        }

        recipeViewModel.isLoading.observe(viewLifecycleOwner){
            when(it){
                true -> binding.progressBar.isVisible = true
                else -> binding.progressBar.isGone = true
            }
        }

        productViewModel.isLoading.observe(viewLifecycleOwner){
            when(it){
                true -> binding.progressBar.isVisible = true
                else -> binding.progressBar.isGone = true
            }
        }

    }

}