package com.gnardini.redux_android.routing

import java.util.*

class Router {

    val viewHistory: Stack<ViewKey> = Stack()

    lateinit var viewContainer: ViewContainer

    fun initializeContainer(viewContainer: ViewContainer) {
        this.viewContainer = viewContainer
        showView(ViewKey.LOGIN)
    }

    fun showView(viewKey: ViewKey) {
        viewHistory.push(viewKey)
        val viewToShow = viewKey.viewFactory.invoke(this)
        viewContainer.drawView(viewToShow)
    }

    fun goBack() {
        if (viewHistory.size == 1) {
            viewContainer.finish()
        } else {
            viewHistory.pop()
            showView(viewHistory.peek())
        }
    }

}
