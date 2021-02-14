/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sorrowblue.jetpack.binding.viewBinding
import com.sorrowblue.jetpack.sample.databinding.FragmentDatabindingBinding

class DataBindingFragment : Fragment(LayoutId.DATA_BIDING) {

    private val binding: FragmentDatabindingBinding by viewBinding()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textviewSecond.text = "no override onCreateView: DataBinding"
        binding.buttonPopup.setOnClickListener { findNavController().popBackStack() }
    }
}
