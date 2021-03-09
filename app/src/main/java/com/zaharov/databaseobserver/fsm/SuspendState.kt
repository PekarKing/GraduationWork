package com.zaharov.databaseobserver.fsm

open class SuspendState(val name: String, val id: Int) {

    open var condition: (() -> Boolean)? = null
    open var action: (suspend () -> Unit)? = null

    var isInvokeOnStart: Boolean = true

    companion object {
        const val TAG = "SuspendState"
    }
}