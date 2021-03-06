package com.gnardini.redux_android.ui.login

import com.gnardini.redux_android.base.Reducer
import com.gnardini.redux_android.ui.login.LoginAction.*

class LoginReducer: Reducer<LoginState, LoginAction> {

    override fun reduce(state: LoginState, action: LoginAction): LoginState {
        return when (action) {
            is LoginPressed -> onLoginPressed(state)
            is EmailChanged -> onEmailUpdated(state, action.email)
            is PasswordChanged -> onPasswordUpdated(state, action.password)
            is LoginSuccess -> onLoginSuccess(state)
            is LoginFailed -> onLoginFailed(state)
        }
    }

    fun onLoginPressed(state: LoginState) = state

    fun onEmailUpdated(state: LoginState, email: String) = state.copy(emailText = email)

    fun onPasswordUpdated(state: LoginState, password: String) = state.copy(passwordText = password)

    fun onLoginSuccess(state: LoginState) = state

    fun onLoginFailed(state: LoginState) = state

}
