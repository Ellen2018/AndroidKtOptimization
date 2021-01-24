package com.ellen.androidktoptimization.ifop

/**
 * 条件语句三要素:
 * 布尔表达式 必须
 * true回调
 * false回调
 */
class SuperCondition {

    private var boolMaps: MutableMap<Int, MutableMap<Boolean, () -> Unit>?> = HashMap()
    private var initPriority = 0
    private var priority: Int = initPriority
    private var endPriority = -1
    private var currentCondition:Boolean = false

    fun check(condition: Boolean): SuperCondition {
        val callbacks: MutableMap<Boolean, () -> Unit> = HashMap()
        boolMaps[priority] = callbacks
        priority++
        currentCondition = condition
        return this
    }

    fun addTrue(function: () -> Unit): SuperCondition {
        if(currentCondition){
            boolMaps[priority-1]?.set(true, function)
        }
        return this
    }

    fun addFalse(function: () -> Unit): SuperCondition {
        if(!currentCondition) {
            boolMaps[priority - 1]?.set(false, function)
        }
        return this
    }

    fun addAgain():SuperCondition{
        if(!currentCondition){
            endPriority = priority-1
        }
        return this
    }

    fun start(){
        for (i in initPriority until priority) {
            val callbacks: MutableMap<Boolean, () -> Unit>? = boolMaps[i]
            if(i == endPriority){
                break
            }
            if (callbacks?.size != 0) {
                val trueCallback: (() -> Unit)? = callbacks?.get(true)
                if (trueCallback != null) {
                    trueCallback()
                }
                val falseCallback: (() -> Unit)? = callbacks?.get(false)
                if (falseCallback != null) {
                    falseCallback()
                }
            }
        }
        init()
    }

    private fun init() {
        boolMaps.clear()
        priority = initPriority
        endPriority = -1
    }
}
