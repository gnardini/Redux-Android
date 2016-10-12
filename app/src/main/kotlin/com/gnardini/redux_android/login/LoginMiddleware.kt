package com.gnardini.redux_android.login

import com.gnardini.redux_android.Store
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

fun LoginView.loginMiddleware() = { store: Store<LoginState, LoginAction>, action: LoginAction ->
    if (action is LoginAction.LoginPressed) {
        Observable.create<LoginAction> { subscriber ->
            subscriber.onNext(LoginAction.LoginSuccess())
            subscriber.onCompleted()
        }.delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { action -> store.dispatchAction(action) }
                .subscribe()
    }
}