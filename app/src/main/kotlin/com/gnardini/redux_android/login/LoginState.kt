package com.gnardini.redux_android.login

import com.gnardini.redux_android.base.State

data class LoginState(
        val emailText: String,
        val passwordText: String) : State {

    fun isLoginButtonEnabled() : Boolean =
            emailText.isNotEmpty() && passwordText.isNotEmpty()

}
