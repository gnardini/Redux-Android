package com.gnardini.redux_android.login

import com.gnardini.redux_android.Store
import com.gnardini.redux_android.repository.UsersRepository
import rx.android.schedulers.AndroidSchedulers

fun LoginView.loginMiddleware(usersRepository: UsersRepository) =
        { store: Store<LoginState, LoginAction>, action: LoginAction ->
            if (action is LoginAction.LoginPressed) {
                val state = store.getState()
                usersRepository.login(state.emailText, state.passwordText)
                        .observeOn(AndroidSchedulers.mainThread())
                        .map { loginSuccess ->
                            if (loginSuccess) {
                                LoginAction.LoginSuccess()
                            } else {
                                LoginAction.LoginFailed()
                            }
                        }
                        .doOnSuccess { action -> store.dispatchAction(action) }
                        .subscribe()
            }
        }