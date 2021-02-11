/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
inline fun <reified T : ViewDataBinding> FragmentActivity.dataBinding() =
    object : ReadOnlyProperty<FragmentActivity, T> {

        override fun getValue(thisRef: FragmentActivity, property: KProperty<*>): T =
            thisRef.requireView().getTag(property.name.hashCode()) as? T
                ?: bind(thisRef.requireView()).also {
                    it.lifecycleOwner = thisRef
                    it.root.setTag(property.name.hashCode(), it)
                }

        private fun bind(view: View): T = DataBindingUtil.bind(view)!!
    }
