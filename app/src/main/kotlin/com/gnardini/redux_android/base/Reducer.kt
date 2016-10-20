package com.gnardini.redux_android.base

interface Reducer<StateType : State, in ActionType : Action> {

    fun reduce(state: StateType, action: ActionType) : StateType

}
