/*
 * (c) 2020-2021 SorrowBlue.
 */

package com.sorrowblue.jetpack.binding

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP
import androidx.core.view.get
import androidx.viewbinding.ViewBinding

/**
 * Set the activity content from a ViewBinding.
 */
@Suppress("unused")
inline fun <reified V : ViewBinding> ComponentActivity.viewBinding() =
    object : AndroidLifecycleViewBindingProperty<ComponentActivity, V>() {

        override fun bind(thisRef: ComponentActivity): V {
            return thisRef.findContentView()?.let { ViewBindingUtil.bind(it) }
                ?: (ViewBindingUtil.inflate(LayoutInflater.from(thisRef)) as V).also {
                    Log.i(
                        "binding-ktx",
                        "The ${thisRef.javaClass.simpleName} has no children. Use the ${V::class.java.simpleName}::inflate to generate a View with inflating."
                    )
                    thisRef.setContentView(it.root)
                }
        }

        override fun getLifecycleOwner(thisRef: ComponentActivity) = thisRef
    }

@RestrictTo(LIBRARY_GROUP)
fun <T : ComponentActivity> T.findContentView(): View? {
    val contentView = findViewById<ViewGroup>(android.R.id.content)
    checkNotNull(contentView) { "The activity(${javaClass.simpleName}) has no view." }
    return when (contentView.childCount) {
        1 -> contentView[0]
        0 -> null
        else -> error("Multiple child views found in the activity(${javaClass.simpleName}) content view.")
    }
}
