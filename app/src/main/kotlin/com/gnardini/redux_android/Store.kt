package com.gnardini.redux_android

import com.gnardini.redux_android.base.Action
import com.gnardini.redux_android.base.Reducer
import com.gnardini.redux_android.base.State
import rx.Observable
import rx.subjects.BehaviorSubject

class Store<StateType: State, ActionType: Action>(
        val reducer: Reducer<StateType, ActionType>) {

    private var state: StateType = reducer.initialState
    private val stateObserver = BehaviorSubject.create(state)
    private val middleware = mutableListOf<(Store<StateType, ActionType>, ActionType) -> Unit>()

    fun dispatchAction(action: ActionType) {
        middleware.forEach { it.invoke(this, action) }

        state = reducer.update(state, action)

        stateObserver.onNext(state)
    }

    fun bindMiddleware(middleware: (Store<StateType, ActionType>, ActionType) -> Unit) {
        this.middleware.add(middleware)
    }

    fun observeState(): Observable<StateType> = stateObserver

    fun getState() = state

}
