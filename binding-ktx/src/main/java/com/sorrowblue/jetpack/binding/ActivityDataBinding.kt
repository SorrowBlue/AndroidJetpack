/*
 * (c) 2020 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
fun <T : ViewDataBinding> FragmentActivity.dataBinding() = ActivityDataBinding<T>()

class ActivityDataBinding<T : ViewDataBinding> internal constructor() :
    ReadOnlyProperty<FragmentActivity, T> {
    private var binding: T? = null

    override fun getValue(thisRef: FragmentActivity, property: KProperty<*>): T {
        return binding ?: bind<T>(thisRef.getContentView()).also {
            it.lifecycleOwner = thisRef
            binding = it
        }
    }

    private fun FragmentActivity.getContentView(): View {
        val view: ViewGroup =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) requireViewById(android.R.id.content)
            else findViewById(android.R.id.content)
        return checkNotNull(view.getChildAt(0)) {
            "Call setContentView or Use Activity's secondary constructor passing layout resource id."
        }
    }

    private fun <T : ViewDataBinding> bind(view: View): T = DataBindingUtil.bind(view)!!
}
