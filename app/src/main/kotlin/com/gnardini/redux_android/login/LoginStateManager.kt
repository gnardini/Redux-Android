package com.gnardini.redux_android.login

import com.gnardini.redux_android.base.StateManager
import com.gnardini.redux_android.login.LoginAction.*
import com.gnardini.redux_android.login.LoginCommand.None
import com.gnardini.redux_android.login.LoginCommand.PerformLogin
import rx.Observable

class LoginStateManager : StateManager<LoginState, LoginAction, LoginCommand> {

    override fun initialState() = LoginState(emailText = "", passwordText = "", toastMessage = null)

    override fun update(state: LoginState, action: LoginAction) : Pair<LoginState, LoginCommand> {
        val updatedState = state.clearMessage()
        return when (action) {
            is LoginPressed -> onLoginPressed(updatedState)
            is EmailChanged -> onEmailUpdated(updatedState, action.email)
            is PasswordChanged -> onPasswordUpdated(updatedState, action.password)
            is LoginResult -> onLoginCompleted(updatedState, action.success)
        }
    }


    override fun applyCommand(state: LoginState, command: LoginCommand): Observable<LoginAction> {
        return when (command) {
            is PerformLogin -> doLogin(state)
            is None -> Observable.empty()
        }
    }

    // Action handlers

    fun onLoginPressed(state: LoginState) : Pair<LoginState, LoginCommand> =
            Pair(state, LoginCommand.PerformLogin)

    fun onEmailUpdated(state: LoginState, email: String) : Pair<LoginState, LoginCommand> =
            Pair(state.copy(emailText = email), LoginCommand.None)

    fun onPasswordUpdated(state: LoginState, password: String) : Pair<LoginState, LoginCommand> =
            Pair(state.copy(passwordText= password), LoginCommand.None)

    fun onLoginCompleted(state: LoginState, success: Boolean): Pair<LoginState, LoginCommand> {
        val message: String = if (success) "Login successful" else "Login failed"
        return Pair(state.copy(toastMessage = message), LoginCommand.None)
    }

    // Command handlers

    fun doLogin(state: LoginState): Observable<LoginAction> {
        // TODO: Make an actual call to API that returns this Observable and handle errors
        return Observable.create { s ->
            s.onNext(LoginResult(success = true))
            s.onCompleted()
        }
    }

}
