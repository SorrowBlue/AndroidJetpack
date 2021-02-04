/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty

@Suppress("unused")
inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    ReadOnlyProperty<Fragment, T> { thisRef, property ->
        thisRef.requireView().getTag(property.name.hashCode()) as? T
            ?: (T::class.java.getMethod("bind", View::class.java).invoke(null, view) as T).also {
                requireView().setTag(property.name.hashCode(), it)
            }
    }
