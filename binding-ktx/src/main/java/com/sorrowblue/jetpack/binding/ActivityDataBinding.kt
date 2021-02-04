/*
 * (c) 2020-2021 SorrowBlue.
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
        val view: ViewGroup = getViewById(android.R.id.content)
        return checkNotNull(view.getChildAt(0)) {
            "Call setContentView or Use Activity's secondary constructor passing layout resource id."
        }
    }

    private fun <T : ViewDataBinding> bind(view: View): T = DataBindingUtil.bind(view)!!

}

internal fun <T : View> FragmentActivity.getViewById(id: Int): T {
    return if (Build.VERSION_CODES.P <= Build.VERSION.SDK_INT) {
        requireViewById(id)
    } else {
        findViewById(id)
    }
}
