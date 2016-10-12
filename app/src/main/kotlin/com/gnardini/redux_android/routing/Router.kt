package com.gnardini.redux_android.routing

import android.view.View
import com.gnardini.redux_android.Store
import com.gnardini.redux_android.login.LoginAction
import com.gnardini.redux_android.login.LoginReducer
import com.gnardini.redux_android.login.LoginView
import com.gnardini.redux_android.pick_friends.PickFriendsReducer
import com.gnardini.redux_android.pick_friends.PickFriendsView
import com.gnardini.redux_android.middleware.logSubscription
import java.util.*

class Router {

    val viewsMap: Map<ViewKey, () -> View> by lazy {
        mapOf(
                Pair(ViewKey.LOGIN, { loginView() }),
                Pair(ViewKey.PICK_FRIENDS, { pickFriendsView() })
        )
    }
    val viewHistory: Stack<ViewKey> = Stack()

    private lateinit var viewContainer: ViewContainer

    fun initializeContainer(viewContainer: ViewContainer) {
        this.viewContainer = viewContainer
        showView(ViewKey.LOGIN)
    }

    fun showView(view: ViewKey) {
        viewHistory.push(view)
        viewsMap[view]?.let { viewToDraw ->
            viewContainer.drawView(viewToDraw.invoke())
        }
    }

    fun goBack() {
        if (viewHistory.size == 1) {
            viewContainer.finish()
        } else {
            viewHistory.pop()
            showView(viewHistory.peek())
        }
    }

    fun loginView(): LoginView {
        val store = Store(LoginReducer())
        store.subscribe(logSubscription())

        store.hookMiddleware { store, action ->
            if (action is LoginAction.LoginSuccess) {
                showView(ViewKey.PICK_FRIENDS)
            }
        }

        return LoginView(store, viewContainer.context)
    }

    fun pickFriendsView(): PickFriendsView {
        val store = Store(PickFriendsReducer())
        store.subscribe(logSubscription())
        return PickFriendsView(store, viewContainer.context)
    }

}
