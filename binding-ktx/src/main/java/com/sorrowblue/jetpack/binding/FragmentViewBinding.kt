/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Set the activity content from a ViewBinding.
 */
inline fun <reified V : ViewBinding> Fragment.viewBinding() =
    object : AndroidLifecycleViewBindingProperty<Fragment, V>() {

        override fun bind(thisRef: Fragment): V {
            return thisRef.view?.let { ViewBindingUtil.bind(it) }
                ?: (ViewBindingUtil.inflate(LayoutInflater.from(requireContext())) as V).also {
                    Log.i(
                        "binding-ktx",
                        "The ${thisRef.javaClass.simpleName} has no view. Use the ${V::class.java.simpleName}::inflate to generate a View with inflating."
                    )
                }
        }

        override fun getLifecycleOwner(thisRef: Fragment) =
            if (thisRef.view != null) thisRef.viewLifecycleOwner else thisRef
    }

