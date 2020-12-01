/*
 * Copyright 2020 SorrowBlue.
 */

package com.sorrowblue.jetpack.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveData<T>(value: T? = null) : MutableLiveData<T>(value) {

    private val pending = AtomicBoolean(false)
    private val observers = mutableSetOf<Observer<in T>>()
    private val internalObserver = Observer<T> { t ->
        if (pending.compareAndSet(true, false)) {
            observers.forEach { it.onChanged(t) }
        }
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        observers.add(observer)
        if (!hasObservers()) {
            super.observe(owner, internalObserver)
        }
    }

    @MainThread
    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    @MainThread
    fun call() {
        value = null
    }
}
