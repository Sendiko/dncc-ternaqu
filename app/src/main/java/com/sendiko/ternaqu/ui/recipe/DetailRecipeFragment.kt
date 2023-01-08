package com.sendiko.ternaqu.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentDetailRecipeBinding
import com.sendiko.ternaqu.repository.helper.SharedViewModel

class DetailRecipeFragment : Fragment() {

    private lateinit var binding: FragmentDetailRecipeBinding

    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailRecipeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailRecipeFragment_to_recipeListFragment)
        }

        sharedViewModel.recipe.observe(viewLifecycleOwner){
            Glide.with(requireContext())
                .load(it.imageUrl)
                .transform(CenterInside(), RoundedCorners(32))
                .into(binding.imageView12)

            binding.textView26.text = it.title
            binding.textView27.text = it.benefit
            binding.textView29.text = it.toolsAndMaterials
            binding.textView31.text = it.steps

        }

    }

}