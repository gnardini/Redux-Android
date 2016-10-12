package com.gnardini.redux_android.middleware

import android.util.Log
import com.gnardini.redux_android.base.State

fun logSubscription(): (State) -> Unit = { state ->
    Log.d(state.javaClass.simpleName, state.toString())
}
