package com.zaharov.databaseobserver.fsm

open class SuspendStateMachine {

    protected open val states = ArrayList<SuspendState>()

    /**
     * Состояние покоя.
     */
    var restingState = SuspendState("Resting State", 0)

    /**
     * Текущее состояние машины. По умолчанию состояние покоя
     */
    var currentState: SuspendState = restingState

    /**
     * Предыдущее состояние машины. По умолчанию состояние покоя
     */
    var previousState: SuspendState = restingState

    open suspend fun startMachine(states: ArrayList<SuspendState>) {
        this.states.addAll(states)
        onNextState()
        do {
            onNextState()
        } while (!isRestingState(currentState))
    }

    /**
     * Устанавливает следующее состояние. Если [state] == null, то выбор происходит из массива состояний.
     */
    open suspend fun onNextState(state: SuspendState? = null) {
        if (state == null) {
            onNextStateFromArray()
        } else {
            onSwitchState(state)
        }
    }

    /**
     * Устанавливает следующее состояние на основе массива состояний.
     */
    protected open suspend fun onNextStateFromArray() {
        if (!states.isNullOrEmpty()) {
            val nextState = states.first { checkCondition(it) }
            onSwitchState(nextState)
        } else {
            onSwitchState(previousState)
        }
    }

    /**
     * Проверяет условие перехода в состояние. Если null, то всегда false
     */
    protected open fun checkCondition(state: SuspendState): Boolean {
        return state.condition?.invoke() ?: false
    }

    /**
     * Меняет состояние и запускает действие.
     */
    protected open suspend fun onSwitchState(state: SuspendState) {
        previousState = currentState
        currentState = state
        if (state.isInvokeOnStart && state.action != null) {
            invokeCurrentState()
        }
    }

    /**
     * Запускает действие, при переходе в состояние.
     */
    protected open suspend fun invokeCurrentState() {
        currentState.action?.invoke()
    }

    /**
     * Проверка, является ли [state] состоянием покоя.
     */
    protected fun isRestingState(state: SuspendState): Boolean {
        return state.id == restingState.id
    }

    companion object {
        const val TAG = "SuspendStateMachine"
    }
}