package com.gnardini.redux_android.routing

import java.util.*

class Router(val viewContainer: ViewContainer, val viewFactory: ViewFactory) {

    val viewHistory: Stack<ViewKey> = Stack()

    fun bindOnBackListener() {
        viewContainer.setOnBackPressListener(goBackListener())
    }

    fun showInitialView() {
        showView(ViewKey.LOGIN)
    }

    fun showView(viewKey: ViewKey) {
        viewHistory.push(viewKey)
        val viewToShow = viewKey.viewFactory(viewFactory, this)
        viewContainer.drawView(viewToShow)
    }

    fun goBackListener(): () -> Unit = {
        if (viewHistory.size == 1) {
            viewContainer.finish()
        } else {
            viewHistory.pop()
            showView(viewHistory.pop())
        }
    }

}
