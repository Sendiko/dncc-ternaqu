package com.sendiko.ternaqu.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentDashboardBinding
import com.sendiko.ternaqu.network.response.ProductItem
import com.sendiko.ternaqu.network.response.RecipeItem
import com.sendiko.ternaqu.repository.AuthViewModel
import com.sendiko.ternaqu.repository.AuthViewModelFactory
import com.sendiko.ternaqu.repository.auth.AuthPreferences
import com.sendiko.ternaqu.repository.helper.SharedViewModel
import com.sendiko.ternaqu.repository.helper.ViewModelFactory
import com.sendiko.ternaqu.repository.product.ProductViewModel
import com.sendiko.ternaqu.repository.recipe.RecipeViewModel
import com.sendiko.ternaqu.ui.auth.dataStore

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

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

    private val pref by lazy {
        AuthPreferences.getInstance(requireNotNull(this.context).dataStore)
    }

    private val authViewModel: AuthViewModel by lazy {
        ViewModelProvider(this, AuthViewModelFactory(pref))[AuthViewModel::class.java]
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

        authViewModel.getUsername().observe(viewLifecycleOwner) { name ->
            "Halo, $name".also { binding.textView3.text = it }
        }

        binding.imageView4.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_profileFragment)
        }

        binding.buttonJoinForum.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_forumFragment)
        }

        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_recipeListFragment)
        }

        binding.button3.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_productListFragment)
        }

        binding.buttonChatNutritionist.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_chatListFragment)
        }

        productViewModel.getProducts().observe(viewLifecycleOwner) {
            binding.rvProducts.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter =
                    ProductAdapter(it, requireContext(), object : ProductAdapter.OnItemClick {
                        override fun onCardRecipeClick(product: ProductItem) {
                            sharedViewModel.saveProduct(product)
                            findNavController().navigate(R.id.action_dashboardFragment_to_detailProductFragment)
                        }

                    })
            }
        }

        recipeViewModel.getRecipe().observe(viewLifecycleOwner) {
            binding.rvResep.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = RecipeAdapter(it, requireContext(), object : RecipeAdapter.OnItemClick {
                    override fun onCardRecipeClick(recipe: RecipeItem) {
                        sharedViewModel.saveRecipe(recipe)
                        findNavController().navigate(R.id.action_dashboardFragment_to_detailRecipeFragment)
                    }

                })
            }
        }

        recipeViewModel.isLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.progressBar7.visibility = View.VISIBLE
                else -> binding.progressBar7.visibility = View.GONE
            }
        }

        productViewModel.isLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.progressBar8.visibility = View.VISIBLE
                else -> binding.progressBar8.visibility = View.GONE
            }
        }

    }

}