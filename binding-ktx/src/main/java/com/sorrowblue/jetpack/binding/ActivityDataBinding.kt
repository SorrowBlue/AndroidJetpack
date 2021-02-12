/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
inline fun <reified T : ViewDataBinding> FragmentActivity.dataBinding() =
    object : ReadOnlyProperty<FragmentActivity, T> {

        override fun getValue(thisRef: FragmentActivity, property: KProperty<*>): T =
            thisRef.requireView().getTag(property.name.hashCode()) as? T
                ?: bind(thisRef.requireView()).also {
                    it.lifecycleOwner = thisRef
                    it.root.setTag(property.name.hashCode(), it)
                }

        private fun bind(view: View): T = DataBindingUtil.bind(view)!!
    }

fun <T : ViewDataBinding> FragmentActivity.dataBinding(@LayoutRes contentLayoutId: Int) =
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
            binding ?: inflate().also {
                binding = it
                it.lifecycleOwner = thisRef
            }

        private fun inflate(): T {
            val inflater = LayoutInflater.from(this@dataBinding)
            return DataBindingUtil.inflate(inflater, contentLayoutId, null, false)
        }
    }
