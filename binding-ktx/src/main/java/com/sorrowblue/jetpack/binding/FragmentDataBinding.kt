/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewDataBinding> Fragment.dataBinding(@LayoutRes layoutRes: Int? = null) =
    object : ReadOnlyProperty<Fragment, T> {

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            thisRef.requireView().getTag(property.name.hashCode()) as? T
                ?: bind(thisRef.requireView()).also {
                    it.lifecycleOwner = thisRef.viewLifecycleOwner
                    it.root.setTag(property.name.hashCode(), it)
                }

        private fun bind(view: View): T = if (layoutRes != null) {
            DataBindingUtil.inflate(
                LayoutInflater.from(requireContext()),
                layoutRes,
                view as ViewGroup,
                false
            )
        } else {
            DataBindingUtil.bind(view)!!
        }
    }
