package com.gnardini.redux_android.routing

import com.gnardini.redux_android.Store
import com.gnardini.redux_android.login.LoginAction
import com.gnardini.redux_android.login.LoginReducer
import com.gnardini.redux_android.login.LoginView
import com.gnardini.redux_android.middleware.logSubscription
import com.gnardini.redux_android.pick_friends.PickFriendsReducer
import com.gnardini.redux_android.pick_friends.PickFriendsView

fun Router.loginView(): LoginView {
    val store = Store(LoginReducer())
    store.subscribe(logSubscription())

    store.bindMiddleware { store, action ->
        if (action is LoginAction.LoginSuccess) {
            showView(ViewKey.PICK_FRIENDS)
        }
    }

    return LoginView(store, networkInjector.usersRepository, viewContainer.context)
}

fun Router.pickFriendsView(): PickFriendsView {
    val store = Store(PickFriendsReducer())
    store.subscribe(logSubscription())
    return PickFriendsView(store, networkInjector.usersRepository, viewContainer.context)
}
