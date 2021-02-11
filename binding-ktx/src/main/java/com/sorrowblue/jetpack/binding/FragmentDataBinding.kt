/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
inline fun <reified T : ViewDataBinding> Fragment.dataBinding() =
    object : ReadOnlyProperty<Fragment, T> {

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            thisRef.requireView().getTag(property.name.hashCode()) as? T
                ?: bind(thisRef.requireView()).also {
                    it.lifecycleOwner = thisRef.viewLifecycleOwner
                    it.root.setTag(property.name.hashCode(), it)
                }

        private fun bind(view: View): T = DataBindingUtil.bind(view)!!
    }
