package com.gnardini.redux_android.ui.login

import com.gnardini.redux_android.base.Action

sealed class LoginAction : Action {

    object LoginPressed: LoginAction()
    class EmailChanged(val email: String) : LoginAction()
    class PasswordChanged(val password: String) : LoginAction()
    class LoginSuccess() : LoginAction()
    class LoginFailed() : LoginAction()

}