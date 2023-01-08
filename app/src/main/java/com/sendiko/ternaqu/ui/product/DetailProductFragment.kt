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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.Snackbar
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentDetailProductBinding
import com.sendiko.ternaqu.repository.helper.SharedViewModel
import com.sendiko.ternaqu.repository.helper.ViewModelFactory
import com.sendiko.ternaqu.repository.product.ProductViewModel
import com.sendiko.ternaqu.ui.loading.LoadingDialogFragment

class DetailProductFragment : Fragment() {

    private lateinit var binding: FragmentDetailProductBinding

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
        binding = FragmentDetailProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailProductFragment_to_productListFragment)
        }

        sharedViewModel.product.observe(viewLifecycleOwner) { productItem ->

            Glide.with(requireContext())
                .load(productItem.imageUrl)
                .transform(CenterInside(), RoundedCorners(32))
                .into(binding.imageView12)

            binding.textTitle.text = productItem.title
            binding.textDesc.text = productItem.description
            productViewModel.getProduct(productItem.id.toString())
                .observe(viewLifecycleOwner) { store ->
                    binding.textDetail.text = buildString {
                        append("Toko: ${store.name} \n")
                        append("Merek: ${productItem.brand} \n")
                        append("Harga: ${productItem.price}")
                    }
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