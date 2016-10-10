package com.gnardini.redux_android.login

import com.gnardini.redux_android.base.State

data class LoginState(
        val emailText: String,
        val passwordText: String,
        val toastMessage: String?) : State {

    fun isLoginButtonEnabled() : Boolean =
            emailText.isNotEmpty() && passwordText.isNotEmpty()

    fun clearMessage(): LoginState = copy(toastMessage = null)

}
