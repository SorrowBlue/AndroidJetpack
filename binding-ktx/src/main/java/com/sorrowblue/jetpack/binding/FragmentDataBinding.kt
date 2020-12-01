/*
 * (c) 2020 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
fun <T : ViewDataBinding> Fragment.dataBinding() = FragmentDataBinding<T>()

class FragmentDataBinding<T : ViewDataBinding> internal constructor() :
    ReadOnlyProperty<Fragment, T> {
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        thisRef.requireView().getTag(property.name.hashCode()) as? T
            ?: bind(thisRef.requireView()).also {
                it.lifecycleOwner = thisRef.viewLifecycleOwner
                it.root.setTag(property.name.hashCode(), it)
            }

    private fun bind(view: View): T = DataBindingUtil.bind<T>(view)!!
}