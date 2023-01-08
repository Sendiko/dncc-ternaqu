package com.sendiko.ternaqu.ui.recipe

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
import com.google.android.material.snackbar.Snackbar
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentRecipeListBinding
import com.sendiko.ternaqu.network.response.RecipeItem
import com.sendiko.ternaqu.repository.helper.SharedViewModel
import com.sendiko.ternaqu.repository.helper.ViewModelFactory
import com.sendiko.ternaqu.repository.recipe.RecipeViewModel
import com.sendiko.ternaqu.ui.loading.LoadingDialogFragment

class RecipeListFragment : Fragment() {

    private lateinit var binding: FragmentRecipeListBinding

    private val sharedViewModel : SharedViewModel by activityViewModels()

    private fun obtainRecipeViewModel(activity: FragmentActivity): RecipeViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this, factory)[RecipeViewModel::class.java]
    }

    private val recipeViewModel by lazy {
        obtainRecipeViewModel(requireNotNull(this.activity))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navBack.setOnClickListener {
            findNavController().navigate(R.id.action_recipeListFragment_to_dashboardFragment)
        }

        recipeViewModel.getRecipe().observe(viewLifecycleOwner) {
            binding.rvRecipeList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter =
                    RecipeListAdapter(it, requireContext(), object : RecipeListAdapter.OnItemClick {
                        override fun onCardRecipeClick(recipe: RecipeItem) {
                            sharedViewModel.saveRecipe(recipe)
                            findNavController().navigate(R.id.action_recipeListFragment_to_detailRecipeFragment)
                        }

                    })
            }
        }
        recipeViewModel.isFailed.observe(viewLifecycleOwner) {
            when {
                it.isFailed -> showSnackbar(it.failedMessage)
            }
        }

        recipeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(isLoading: Boolean) {
        var loadingDialogFragment = LoadingDialogFragment()
        when {
            isLoading -> {
                loadingDialogFragment.show(parentFragmentManager)
            }
            else -> {
                loadingDialogFragment =
                    parentFragmentManager.findFragmentByTag(LoadingDialogFragment().FRAGMENT_TAG) as LoadingDialogFragment
                loadingDialogFragment.dismiss()
            }
        }
    }

}