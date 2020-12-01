/*
 * (c) 2020 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
fun <T : ViewBinding> Fragment.viewBinding(bind: (View) -> T) = FragmentViewBinding(bind)

class FragmentViewBinding<T : ViewBinding> internal constructor(private val bind: (View) -> T) :
    ReadOnlyProperty<Fragment, T> {
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        thisRef.requireView().getTag(property.name.hashCode()) as? T
            ?: bind(thisRef.requireView()).also { it.root.setTag(property.name.hashCode(), it) }
}