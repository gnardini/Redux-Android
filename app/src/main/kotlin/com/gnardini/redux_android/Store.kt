package com.gnardini.redux_android

import com.gnardini.redux_android.base.Action
import com.gnardini.redux_android.base.Command
import com.gnardini.redux_android.base.State
import com.gnardini.redux_android.base.StateManager

class Store<StateType: State, ActionType: Action, CommandType : Command>(
        val stateManager: StateManager<StateType, ActionType, CommandType>) {

    private var state: StateType = stateManager.initialState()
    private val subscribers = mutableListOf<(StateType) -> Unit>()

    fun dispatchAction(action: ActionType) {
        val result : Pair<StateType, CommandType> = stateManager.update(state, action)
        this.state = result.first

        stateManager.applyCommand(state, result.second)
                .forEach { action -> dispatchAction(action) }

        subscribers.forEach { it.invoke(this.state) }
    }

    fun subscribe(subscriber: (StateType) -> Unit) {
        subscribers.add(subscriber)
    }

    fun getState() = state

}
