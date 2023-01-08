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
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentProductListBinding
import com.sendiko.ternaqu.network.response.ProductItem
import com.sendiko.ternaqu.repository.helper.SharedViewModel
import com.sendiko.ternaqu.repository.helper.ViewModelFactory
import com.sendiko.ternaqu.repository.product.ProductViewModel

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

    }

}