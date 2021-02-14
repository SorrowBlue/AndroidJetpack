/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.sample

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sorrowblue.jetpack.binding.viewBinding
import com.sorrowblue.jetpack.sample.databinding.FragmentViewbindingBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MaterialViewBindingDialogFragment : DialogFragment(LayoutId.VIEW_BIDING) {

    private val binding: FragmentViewbindingBinding by viewBinding()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textviewSecond.text = "no override onCreateView\n MaterialAlertDialog ViewBinding"
        binding.buttonPopup.setOnClickListener { findNavController().popBackStack() }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
    }
}
