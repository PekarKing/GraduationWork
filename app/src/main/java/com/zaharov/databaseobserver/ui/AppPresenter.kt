package com.zaharov.databaseobserver.ui

import android.util.Log
import com.zaharov.databaseobserver.fsm.State
import com.zaharov.databaseobserver.fsm.StateMachine
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

class AppPresenter : MvpPresenter<AppView>() {

    private var stateMachine = StateMachine()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.d(TAG, "onFirstViewAttach")
    }

    fun a() {
        val red = State("red", 1)
        val yellow = State("yellow", 2)
        val green = State("green", 3)

        red.condition =
            { (stateMachine.previousState?.id == 3 || stateMachine.currentState?.id == 0) }
        yellow.condition =
            { (stateMachine.currentState?.id == 1 || stateMachine.currentState?.id == 3) }
        green.condition =
            { (stateMachine.previousState?.id == 1) }

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

    companion object {
        private const val TAG = "AppPresenter"
    }
}