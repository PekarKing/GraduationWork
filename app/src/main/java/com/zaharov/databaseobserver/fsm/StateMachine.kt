package com.zaharov.databaseobserver.fsm


open class StateMachine {

    var currentState: State? = State("rest", 0)
    var previousState: State? = null
    private val states = ArrayList<State>()

    suspend fun startMachine(states: ArrayList<State>) {
        this.states.addAll(states)
        var state1 = states.first { it.condition?.invoke() == true }
        previousState = currentState
        currentState = state1
        do {
            currentState?.action?.invoke()
            state1 = states.first { it.condition?.invoke() == true }
            previousState = currentState
            currentState = state1
        } while (currentState?.id != 0)
    }
}

class State(val name: String, val id: Int) {

    var condition: (() -> Boolean)? = null
    var action: (suspend () -> Unit)? = null
}
