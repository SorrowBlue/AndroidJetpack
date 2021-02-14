/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.sample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sorrowblue.jetpack.binding.viewBinding
import com.sorrowblue.jetpack.sample.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(R.layout.fragment_first) {

    private val binding: FragmentFirstBinding by viewBinding()

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return binding.root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonDialogData.onClickNavigate(R.id.action_firstFragment_to_dataBindingDialogFragment)
        binding.buttonDialogView.onClickNavigate(R.id.action_firstFragment_to_viewBindingDialogFragment)
        binding.buttonFragmentData.onClickNavigate(R.id.action_firstFragment_to_dataBindingFragment)
        binding.buttonFragmentView.onClickNavigate(R.id.action_firstFragment_to_viewBindingFragment)
        binding.buttonMaterialDialogData.onClickNavigate(R.id.action_firstFragment_to_materialDataBindingDialogFragment2)
        binding.buttonMaterialDialogView.onClickNavigate(R.id.action_firstFragment_to_materialViewBindingDialogFragment2)
    }
}

private fun View.onClickNavigate(actionId: Int) = setOnClickListener {
    findNavController().navigate(actionId)
}
