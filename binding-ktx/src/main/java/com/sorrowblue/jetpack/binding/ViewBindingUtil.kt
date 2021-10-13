/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding

/**
 * Provides extension functions for ViewBinding.
 */
object ViewBindingUtil {

    /**
     * Wrap the ViewBinding.bind(view: View).
     */
    inline fun <reified V : ViewBinding> bind(view: View): V {
        return V::class.java.getMethod("bind", View::class.java).invoke(null, view) as V
    }

    /**
     * Wrap the ViewBinding.inflate(inflater: LayoutInflater).
     */
    inline fun <reified V : ViewBinding> inflate(inflater: LayoutInflater): V {
        return V::class.java.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, inflater) as V
    }
}
