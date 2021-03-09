package com.zaharov.databaseobserver.ui.checker

import com.zaharov.databaseobserver.fsm.ControlledStateMachine
import com.zaharov.databaseobserver.fsm.State
import com.zaharov.databaseobserver.ui.base.BasePresenter
import kotlin.properties.Delegates

class StatesPresenter : BasePresenter<StatesView>() {

    var oneState: Boolean by Delegates.observable(false) { _, _, _ -> onHandleState() }
    var twoState: Boolean by Delegates.observable(false) { _, _, _ -> onHandleState() }
    var threeState: Boolean by Delegates.observable(false) { _, _, _ -> onHandleState() }

    private val stateMachine = ControlledStateMachine()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val stateOne = State("Состояние 1", 1)
        val stateTwo = State("Состояние 2", 2)
        val stateThree = State("Состояние 3", 3)

        val stateOneAndTwo = State("Состояние Альфа", 4)
        val stateOneAndThree = State("Состояние Бетта", 5)
        val stateTwoAndThree = State("Состояние Гамма", 6)

        val stateAll = State("Все состояния", 7)
        val stateRest = State("Состояние покоя", 0)

        stateOne.condition = { oneState && !twoState && !threeState }
        stateTwo.condition = { !oneState && twoState && !threeState }
        stateThree.condition = { !oneState && !twoState && threeState }

        stateOneAndTwo.condition = { oneState && twoState && !threeState }
        stateOneAndThree.condition = { oneState && !twoState && threeState }
        stateTwoAndThree.condition = { !oneState && twoState && threeState }

        stateAll.condition = { oneState && twoState && threeState }
        stateRest.condition = { !oneState && !twoState && !threeState }

        val states = arrayListOf(stateOne,
            stateTwo,
            stateThree,
            stateOneAndTwo,
            stateOneAndThree,
            stateTwoAndThree,
            stateAll,
            stateRest)

        states.forEach { it.action = { viewState.showState(it.name) } }

        stateMachine.restingState = stateRest
        stateMachine.states.addAll(states)
        onHandleState()
    }

    private fun onHandleState() {
        stateMachine.onNextState()
    }
}