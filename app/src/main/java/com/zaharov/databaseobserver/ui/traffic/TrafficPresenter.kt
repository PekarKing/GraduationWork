package com.zaharov.databaseobserver.ui.traffic

import com.zaharov.databaseobserver.fsm.SuspendState
import com.zaharov.databaseobserver.fsm.InfiniteStateMachine
import com.zaharov.databaseobserver.ui.base.BasePresenter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moxy.presenterScope

class TrafficPresenter : BasePresenter<TrafficView>() {

    private var stateMachine = InfiniteStateMachine()

    fun startStateMachine() {
        val red = SuspendState("red", 1)
        val yellow = SuspendState("yellow", 2)
        val green = SuspendState("green", 3)

        red.condition =
            { (stateMachine.previousState.id == 3 || stateMachine.currentState.id == 0) }
        yellow.condition =
            { (stateMachine.currentState.id == 1 || stateMachine.currentState.id == 3) }
        green.condition =
            { (stateMachine.previousState.id == 1) }

        red.action = {
            viewState.onChangeSignal(1)
            delay(1000)
        }
        yellow.action = {
            viewState.onChangeSignal(2)
            delay(1000)
        }
        green.action = {
            viewState.onChangeSignal(3)
            delay(1000)
        }

        presenterScope.launch {
            stateMachine.startMachine(arrayListOf(red, yellow, green))
        }
    }
}