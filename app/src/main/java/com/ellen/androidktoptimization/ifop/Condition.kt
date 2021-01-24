package com.ellen.androidktoptimization.ifop

import android.util.Log

/**
 * 条件语句三要素:
 * 布尔表达式 必须
 * true回调
 * false回调
 */
class Condition {

    private var boolMaps: MutableMap<Int, MutableMap<Boolean, () -> Unit>?> = HashMap()
    private var initPriority = 0
    private var priority: Int = initPriority
    private var endPriority = -1

    private fun init(){
        boolMaps.clear()
        priority = initPriority
        endPriority = -1
    }

    fun check(condition: Boolean, trueCallback: () -> Unit, falseCallback: () -> Unit): Condition {
        val callbacks: MutableMap<Boolean, () -> Unit> = HashMap()
        if (condition) {
            Log.e("Ellen2021","执行没true")
            callbacks[true] = trueCallback
        } else {
            Log.e("Ellen2021","执行没false")
            callbacks[false] = falseCallback
        }
        Log.e("Ellen2021","执行没check")
        boolMaps[priority] = callbacks
        priority++
        return this
    }

    fun checkNoCallback(condition: Boolean): Condition {
        if (!condition) {
            //设置结束执行的位置
            endPriority = priority
        }
        boolMaps[priority] = null
        priority++
        return this
    }

    fun checkFalseCallback(condition: Boolean, falseCallback: () -> Unit): Condition {
        val callbacks: MutableMap<Boolean, () -> Unit>?
        if (condition) {
            callbacks = null
        } else {
            callbacks = HashMap()
            callbacks[false] = falseCallback
        }
        boolMaps[priority] = callbacks
        priority++
        return this
    }

    fun checkTrueCallback(condition: Boolean, trueCallback: () -> Unit): Condition {
        val callbacks: MutableMap<Boolean, () -> Unit>?
        if (condition) {
            callbacks = HashMap()
            callbacks[true] = trueCallback
        } else {
            callbacks = null
        }
        boolMaps[priority] = callbacks
        priority++
        return this
    }

    fun start() {
        for (i in initPriority until priority) {
            val callbacks: MutableMap<Boolean, () -> Unit>? = boolMaps[i]
            if(i == endPriority){
                break
            }
            if (callbacks != null) {
                val trueCallback: (() -> Unit)? = callbacks[true]
                if (trueCallback != null) {
                    trueCallback()
                }
                val falseCallback: (() -> Unit)? = callbacks[false]
                if (falseCallback != null) {
                    falseCallback()
                }
            }
        }
        init()
    }
}