package com.gnardini.redux_android.routing

import android.content.Context
import android.view.View

interface ViewContainer {

    val context: Context

    fun drawView(view: View)

    fun finish()

}