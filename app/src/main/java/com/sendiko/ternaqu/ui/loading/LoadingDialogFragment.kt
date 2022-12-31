package com.sendiko.ternaqu.ui.loading

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.sendiko.ternaqu.R

class LoadingDialogFragment : DialogFragment() {

    private fun newInstance() = LoadingDialogFragment()
    val FRAGMENT_TAG = "loading"

    fun show(supportFragmentManager: FragmentManager): LoadingDialogFragment {
        val dialog = newInstance()
        dialog.isCancelable = true
        dialog.show(supportFragmentManager, FRAGMENT_TAG)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return requireActivity().layoutInflater.inflate(
            R.layout.fragment_loading_dialog,
            container,
            false
        )
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.also { window ->
            window.attributes.also { attributes ->
                attributes.dimAmount = 0.2f
                window.attributes = attributes
            }
        }
    }
}