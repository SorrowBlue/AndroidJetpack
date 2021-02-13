/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    object : ReadOnlyProperty<Fragment, T> {

        private var binding: T? = null

        init {
            lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    viewLifecycleOwnerLiveData.observe(this@viewBinding) {
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
            binding ?: kotlin.runCatching {
                thisRef.requireView().getTag(property.name.hashCode()) as? T
                    ?: bind(thisRef.requireView()).also {
                        it.root.setTag(property.name.hashCode(), it)
                    }
            }.getOrElse { binding ?: inflate().also { binding = it } }

        private fun bind(view: View): T =
            T::class.java.getMethod("bind", View::class.java).invoke(null, view) as T

        private fun inflate(): T {
            val inflater = LayoutInflater.from(requireContext())
            return T::class.java.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            ).invoke(null, inflater, null, false) as T
        }
    }
