package com.gnardini.redux_android.routing

import com.gnardini.redux_android.Store
import com.gnardini.redux_android.injector.NetworkInjector
import com.gnardini.redux_android.middleware.logSubscription
import com.gnardini.redux_android.ui.login.LoginMiddleware
import com.gnardini.redux_android.ui.login.LoginReducer
import com.gnardini.redux_android.ui.login.LoginState
import com.gnardini.redux_android.ui.login.LoginView
import com.gnardini.redux_android.ui.pick_friends.PickFriendsMiddleware
import com.gnardini.redux_android.ui.pick_friends.PickFriendsReducer
import com.gnardini.redux_android.ui.pick_friends.PickFriendsReducer.PickFriendsState
import com.gnardini.redux_android.ui.pick_friends.PickFriendsView
import java.util.*

class ViewFactory(
        val viewContainer: ViewContainer,
        val networkInjector: NetworkInjector) {

    fun loginView(router: Router): LoginView {
        val store = Store(LoginReducer(), LoginState(emailText = "", passwordText = ""))
        store.observeState().subscribe(logSubscription())
        store.bindMiddleware(LoginMiddleware.loginNavigation(router))
        store.bindMiddleware(LoginMiddleware.loginRequests(networkInjector.usersRepository))
        return LoginView(store, viewContainer.context)
    }

    fun pickFriendsView(router: Router): PickFriendsView {
        val store = Store(PickFriendsReducer(), PickFriendsState(LinkedList()))
        val pickFriendsView = PickFriendsView(store, viewContainer.context)

        store.observeState().subscribe(logSubscription())
        store.bindMiddleware(PickFriendsMiddleware.peopleFetcher(networkInjector.usersRepository))
        store.bindMiddleware(PickFriendsMiddleware.peopleListRefresher(pickFriendsView))

        store.dispatchAction(PickFriendsReducer.PickFriendsAction.FetchPeople)
        return pickFriendsView
    }

}
