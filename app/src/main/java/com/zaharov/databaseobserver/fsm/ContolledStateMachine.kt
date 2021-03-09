package com.zaharov.databaseobserver.fsm

import kotlinx.coroutines.runBlocking

open class ControlledStateMachine(open val states: ArrayList<State> = ArrayList()) {

    /**
     * Состояние покоя.
     */
    var restingState = State("Resting State", 0)

    /**
     * Текущее состояние машины. По умолчанию состояние покоя
     */
    var currentState: State = restingState

    /**
     * Предыдущее состояние машины. По умолчанию состояние покоя
     */
    var previousState: State = restingState

    /**
     * Устанавливает следующее состояние. Если [state] == null, то выбор происходит из массива состояний.
     */
    open fun onNextState(state: State? = null) {
        if (state == null) {
            onNextStateFromArray()
        } else {
            onSwitchState(state)
        }
    }

    /**
     * Устанавливает следующее состояние на основе массива состояний.
     */
    protected open fun onNextStateFromArray() {
        if (!states.isNullOrEmpty()) {
            val nextState = states.firstOrNull { checkCondition(it) } ?: restingState
            onSwitchState(nextState)
        } else {
            onSwitchState(previousState)
        }
    }

    /**
     * Проверяет условие перехода в состояние. Если null, то всегда false
     */
    protected open fun checkCondition(state: State): Boolean {
        return state.condition?.invoke() ?: false
    }

    /**
     * Меняет состояние и запускает действие.
     */
    protected open fun onSwitchState(state: State) {
        previousState = currentState
        currentState = state
        if (state.isInvokeOnStart && state.action != null) {
            invokeCurrentState()
        }
    }

    /**
     * Запускает действие, при переходе в состояние.
     */
    protected open fun invokeCurrentState() {
        runBlocking {
            currentState.action?.invoke()
        }
    }

    /**
     * Проверка, является ли [state] состоянием покоя.
     */
    protected fun isRestingState(state: State): Boolean {
        return state.id == restingState.id
    }

    companion object {
        const val TAG = "StateMachine"
    }
}
