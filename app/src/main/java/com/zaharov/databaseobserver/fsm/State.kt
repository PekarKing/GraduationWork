package com.zaharov.databaseobserver.fsm

open class State(val name: String, val id: Int) {

    open var condition: (() -> Boolean)? = null
    open var action: (() -> Unit)? = null

    var isInvokeOnStart: Boolean = true

    companion object {
        const val TAG = "State"
    }
}