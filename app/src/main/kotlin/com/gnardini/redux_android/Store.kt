package com.gnardini.redux_android

import com.gnardini.redux_android.base.Action
import com.gnardini.redux_android.base.Command
import com.gnardini.redux_android.base.State
import com.gnardini.redux_android.base.StateManager

class Store<S: State, A: Action, C: Command> (val stateManager: StateManager<S, A, C>) {

    private var state: S = stateManager.initialState()
    private var subscribers = mutableListOf<(S) -> Unit>()

    fun dispatchAction(action: A) {
        val result : Pair<S, C> = stateManager.update(state, action)
        this.state = result.first

        stateManager.applyCommand(state, result.second)
                .forEach { action -> dispatchAction(action) }

        subscribers.forEach { it.invoke(this.state) }
    }

    fun subscribe(subscriber: (S) -> Unit) {
        subscribers.add(subscriber)
    }

    fun getState() = state

}
