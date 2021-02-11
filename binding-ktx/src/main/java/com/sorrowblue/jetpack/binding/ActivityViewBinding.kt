/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
inline fun <reified T : ViewBinding> FragmentActivity.viewBinding(isInflate: Boolean = false) =
    object : ReadOnlyProperty<FragmentActivity, T> {

        override fun getValue(thisRef: FragmentActivity, property: KProperty<*>): T =
            thisRef.requireView().getTag(property.name.hashCode()) as? T
                ?: bind(thisRef.requireView()).also { it.root.setTag(property.name.hashCode(), it) }

        private fun bind(view: View): T = if (isInflate) {
            T::class.java.getMethod(
                "inflate",
                layoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            ).invoke(null, LayoutInflater.from(applicationContext), view as ViewGroup, false) as T
        } else {
            T::class.java.getMethod("bind", View::class.java).invoke(null, view) as T
        }
    }

fun FragmentActivity.requireView(): View {
    val view: ViewGroup =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) requireViewById(android.R.id.content)
        else findViewById(android.R.id.content)
    return checkNotNull(view.getChildAt(0)) {
        "Call setContentView or Use Activity's secondary constructor passing layout resource id."
    }
}
