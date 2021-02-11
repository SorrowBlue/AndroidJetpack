/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    object : ReadOnlyProperty<Fragment, T> {

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            thisRef.requireView().getTag(property.name.hashCode()) as? T
                ?: bind(thisRef.requireView()).also { it.root.setTag(property.name.hashCode(), it) }

        private fun bind(view: View): T =
            T::class.java.getMethod("bind", View::class.java).invoke(null, view) as T
    }
