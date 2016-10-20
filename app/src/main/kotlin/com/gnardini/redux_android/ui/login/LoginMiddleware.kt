package com.gnardini.redux_android.ui.login

import com.gnardini.redux_android.Store
import com.gnardini.redux_android.repository.UsersRepository
import com.gnardini.redux_android.routing.Router
import com.gnardini.redux_android.routing.ViewKey
import rx.android.schedulers.AndroidSchedulers

object LoginMiddleware {

    fun loginNavigation(router: Router): (Store<LoginState, LoginAction>, LoginAction) -> Unit = {
        store, action ->
        if (action is LoginAction.LoginSuccess) {
            router.showView(ViewKey.PICK_FRIENDS)
        }
    }

    fun loginRequests(usersRepository: UsersRepository):
            (Store<LoginState, LoginAction>, LoginAction) -> Unit = {
        store, action ->
        if (action is LoginAction.LoginPressed) {
            val state = store.getState()
            usersRepository.login(state.emailText, state.passwordText)
                    .map { loginSuccess ->
                        if (loginSuccess) {
                            LoginAction.LoginSuccess()
                        } else {
                            LoginAction.LoginFailed()
                        }
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess { action -> store.dispatchAction(action) }
                    .subscribe()
        }
    }


}
