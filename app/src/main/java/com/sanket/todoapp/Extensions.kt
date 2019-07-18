package com.sanket.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * Created by Sanket on 2019-07-18.
 */
fun <T> Any.unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun ViewGroup.inflateView(@LayoutRes viewId: Int) = LayoutInflater.from(this.context).inflate(viewId, this, false)