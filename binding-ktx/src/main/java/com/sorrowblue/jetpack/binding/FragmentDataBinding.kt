/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
fun <T : ViewDataBinding> Fragment.dataBinding() =
    object : ReadOnlyProperty<Fragment, T> {

        @Suppress("UNCHECKED_CAST")
        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            thisRef.requireView().getTag(property.name.hashCode()) as? T
                ?: bind(thisRef.requireView()).also {
                    it.lifecycleOwner = thisRef.viewLifecycleOwner
                    it.root.setTag(property.name.hashCode(), it)
                }

        private fun bind(view: View): T = DataBindingUtil.bind(view)!!
    }

fun <T : ViewDataBinding> Fragment.dataBinding(@LayoutRes contentLayoutId: Int) =
    object : ReadOnlyProperty<Fragment, T> {

        private var binding: T? = null

        init {
            lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    viewLifecycleOwnerLiveData.observe(this@dataBinding) {
                        it?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                            override fun onDestroy(owner: LifecycleOwner) {
                                binding = null
                            }
                        })
                    }
                }
            })
        }

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            binding ?: inflate().also {
                binding = it
                it.lifecycleOwner = thisRef.viewLifecycleOwner
            }

        private fun inflate(): T {
            val inflater = LayoutInflater.from(requireContext())
            return DataBindingUtil.inflate(inflater, contentLayoutId, null, false)
        }
    }
