/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty

@Suppress("unused")
inline fun <reified T : ViewBinding> FragmentActivity.viewBinding() =
    ReadOnlyProperty<FragmentActivity, T> { thisRef, property ->
        thisRef.requireView().getTag(property.name.hashCode()) as? T
            ?: (T::class.java.getMethod("bind", View::class.java)
                .invoke(null, thisRef.requireView()) as T).also {
                thisRef.requireView().setTag(property.name.hashCode(), it)

            }
    }

fun FragmentActivity.requireView(): View {
    val view: ViewGroup = getViewById(android.R.id.content)
    return checkNotNull(view.getChildAt(0)) {
        "Call setContentView or Use Activity's secondary constructor passing layout resource id."
    }
}
