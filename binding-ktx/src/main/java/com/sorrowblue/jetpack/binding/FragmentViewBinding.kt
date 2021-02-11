/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding(isInflate: Boolean = false) =
    object : ReadOnlyProperty<Fragment, T> {

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            thisRef.requireView().getTag(property.name.hashCode()) as? T
                ?: bind(thisRef.requireView()).also { it.root.setTag(property.name.hashCode(), it) }

        private fun bind(view: View): T = if (isInflate) {
            T::class.java.getMethod(
                "inflate",
                layoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            ).invoke(null, LayoutInflater.from(requireContext()), view as ViewGroup, false) as T
        } else {
            T::class.java.getMethod("bind", View::class.java).invoke(null, view) as T
        }
    }
