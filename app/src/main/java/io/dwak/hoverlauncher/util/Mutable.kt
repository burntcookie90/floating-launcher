package io.dwak.hoverlauncher.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlin.reflect.KProperty

class Mutable<T>(private val mutableLiveData: MutableLiveData<T>) {
  operator fun getValue(thisRef: Any, property: KProperty<*>): LiveData<T> = mutableLiveData
}