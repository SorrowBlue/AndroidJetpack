/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
inline fun <reified T : ViewBinding> FragmentActivity.viewBinding() =
    object : ReadOnlyProperty<FragmentActivity, T> {

        private var binding: T? = null

        init {
            lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }
            })
        }

        override fun getValue(thisRef: FragmentActivity, property: KProperty<*>): T =
            kotlin.runCatching {
                thisRef.requireView().getTag(property.name.hashCode()) as? T
                    ?: bind(thisRef.requireView()).also {
                        it.root.setTag(
                            property.name.hashCode(),
                            it
                        )
                    }
            }.getOrElse {
                binding ?: inflate().also { binding = it }
            }

        private fun bind(view: View): T =
            T::class.java.getMethod("bind", View::class.java).invoke(null, view) as T

        private fun inflate(): T {
            val inflater = LayoutInflater.from(this@viewBinding)
            return T::class.java.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            ).invoke(null, inflater, null, false) as T
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
