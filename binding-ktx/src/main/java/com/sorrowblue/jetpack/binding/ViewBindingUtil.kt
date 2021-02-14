/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding

object ViewBindingUtil {

    inline fun <reified V : ViewBinding> bind(view: View): V {
        return V::class.java.getMethod("bind", View::class.java).invoke(null, view) as V
    }

    inline fun <reified V : ViewBinding> inflate(inflater: LayoutInflater): V {
        return V::class.java.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, inflater) as V
    }
}
