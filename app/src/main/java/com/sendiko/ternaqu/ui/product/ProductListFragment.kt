package com.sendiko.ternaqu.ui.product

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
import com.sendiko.ternaqu.databinding.FragmentProductListBinding
import com.sendiko.ternaqu.network.response.ProductItem
import com.sendiko.ternaqu.repository.helper.SharedViewModel
import com.sendiko.ternaqu.repository.helper.ViewModelFactory
import com.sendiko.ternaqu.repository.product.ProductViewModel
import com.sendiko.ternaqu.ui.loading.LoadingDialogFragment

class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private fun obtainProductViewModel(activity: FragmentActivity): ProductViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this, factory)[ProductViewModel::class.java]
    }

    private val productViewModel by lazy {
        obtainProductViewModel(requireNotNull(this.activity))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navBack.setOnClickListener {
            findNavController().navigate(R.id.action_productListFragment_to_dashboardFragment)
        }

        productViewModel.getProducts().observe(viewLifecycleOwner) {
            binding.rvProductsList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ProductListAdapter(
                    it,
                    requireContext(),
                    object : ProductListAdapter.OnItemClick {
                        override fun onCardRecipeClick(product: ProductItem) {
                            sharedViewModel.saveProduct(product)
                            findNavController().navigate(R.id.action_productListFragment_to_detailProductFragment)
                        }

                    })
            }
        }

        productViewModel.isFailed.observe(viewLifecycleOwner) {
            when {
                it.isFailed -> showSnackbar(it.failedMessage)
            }
        }

        productViewModel.isLoading.observe(viewLifecycleOwner) {
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