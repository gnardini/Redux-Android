package com.gnardini.redux_android.routing

import android.view.View
import com.gnardini.redux_android.Store
import com.gnardini.redux_android.login.LoginAction
import com.gnardini.redux_android.login.LoginReducer
import com.gnardini.redux_android.login.LoginView
import com.gnardini.redux_android.middleware.logSubscription
import com.gnardini.redux_android.pick_friends.PickFriendsReducer
import com.gnardini.redux_android.pick_friends.PickFriendsView

enum class ViewKey(val viewFactory: (Router) -> View) {
    LOGIN({ router -> ViewFactory.loginView(router) }),
    PICK_FRIENDS({ router -> ViewFactory.pickFriendsView(router) });

    companion object ViewFactory {

        fun loginView(router: Router): LoginView {
            val store = Store(LoginReducer())
            store.subscribe(logSubscription())

            store.hookMiddleware { store, action ->
                if (action is LoginAction.LoginSuccess) {
                    router.showView(ViewKey.PICK_FRIENDS)
                }
            }

            return LoginView(store, router.viewContainer.context)
        }

        fun pickFriendsView(router: Router): PickFriendsView {
            val store = Store(PickFriendsReducer())
            store.subscribe(logSubscription())
            return PickFriendsView(store, router.viewContainer.context)
        }

    }

}
