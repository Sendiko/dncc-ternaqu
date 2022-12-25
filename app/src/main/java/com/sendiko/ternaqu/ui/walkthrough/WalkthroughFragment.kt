package com.sendiko.ternaqu.ui.walkthrough

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sendiko.ternaqu.R
import com.sendiko.ternaqu.databinding.FragmentWalkthroughBinding

class WalkthroughFragment : Fragment() {

    private lateinit var binding : FragmentWalkthroughBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWalkthroughBinding.inflate(layoutInflater)
        return binding.root
    }
}