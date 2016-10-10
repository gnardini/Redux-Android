package com.gnardini.redux_android.base

import rx.Observable

interface StateManager<S: State, A: Action, C: Command> {

    fun initialState() : S
    fun update(state: S, action: A) : Pair<S, C>
    fun applyCommand(state: S, command: C) : Observable<A>
}
