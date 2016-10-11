package com.gnardini.redux_android.base

import rx.Observable

interface StateManager<StateType : State, ActionType : Action, CcommandType : Command> {

    fun initialState() : StateType
    fun update(state: StateType, action: ActionType) : Pair<StateType, CcommandType>
    fun applyCommand(state: StateType, command: CcommandType) : Observable<ActionType>
}
