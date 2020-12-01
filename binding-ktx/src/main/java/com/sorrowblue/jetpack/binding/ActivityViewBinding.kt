/*
 * (c) 2020 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
fun <T : ViewBinding> FragmentActivity.viewBinding(bind: (View) -> T) =
    ActivityViewBindingDelegate(bind)

class ActivityViewBindingDelegate<T : ViewBinding> internal constructor(private val bind: (View) -> T) :
    ReadOnlyProperty<FragmentActivity, T> {
    private var binding: T? = null

    override fun getValue(thisRef: FragmentActivity, property: KProperty<*>): T =
        binding ?: bind(thisRef.getContentView()).also { binding = it }

    private fun FragmentActivity.getContentView(): View {
        val view: ViewGroup =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) requireViewById(android.R.id.content)
            else findViewById(android.R.id.content)
        return checkNotNull(view.getChildAt(0)) {
            "Call setContentView or Use Activity's secondary constructor passing layout resource id."
        }
    }
}