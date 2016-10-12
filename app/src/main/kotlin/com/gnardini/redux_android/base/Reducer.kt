package com.gnardini.redux_android.base

interface Reducer<StateType : State, in ActionType : Action> {

    val initialState: StateType

    fun update(state: StateType, action: ActionType) : StateType

}
