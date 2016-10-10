package com.gnardini.redux_android.login

import com.gnardini.redux_android.base.Command

sealed class LoginCommand : Command {

    object None: LoginCommand()
    object PerformLogin: LoginCommand()

}