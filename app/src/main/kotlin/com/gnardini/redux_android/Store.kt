package com.gnardini.redux_android

import com.gnardini.redux_android.base.Action
import com.gnardini.redux_android.base.Reducer
import com.gnardini.redux_android.base.State

class Store<StateType: State, ActionType: Action>(
        val reducer: Reducer<StateType, ActionType>) {

    private var state: StateType = reducer.initialState
    private val subscribers = mutableListOf<(StateType) -> Unit>()
    private val middleware = mutableListOf<(Store<StateType, ActionType>, ActionType) -> Unit>()

    fun dispatchAction(action: ActionType) {
        middleware.forEach { it.invoke(this, action) }

        state = reducer.update(state, action)

        subscribers.forEach { it.invoke(this.state) }
    }

    fun bindMiddleware(middleware: (Store<StateType, ActionType>, ActionType) -> Unit) {
        this.middleware.add(middleware)
    }

    fun subscribe(subscriber: (StateType) -> Unit) {
        subscribers.add(subscriber)
    }

    fun getState() = state

}
