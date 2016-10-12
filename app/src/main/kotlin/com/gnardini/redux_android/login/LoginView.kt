package com.gnardini.redux_android.login

import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.gnardini.redux_android.Store
import com.gnardini.redux_android.login.LoginAction.EmailChanged
import com.gnardini.redux_android.login.LoginAction.PasswordChanged
import trikita.anvil.Anvil
import trikita.anvil.DSL.*

class LoginView(val store: Store<LoginState, LoginAction>, context: Context) :
        FrameLayout(context) {

    init {
        store.hookMiddleware(loginMiddleware())
        store.subscribe { stateUpdated() }
        populateView()
    }

    fun stateUpdated() {
        Anvil.render()
    }

    fun populateView() {
        Anvil.mount(this) {
            linearLayout {
                orientation(LinearLayout.VERTICAL)
                gravity(CENTER)

                editText {
                    width(dip(150))
                    margin(dip(12))
                    hint("Email")
                    text(store.getState().emailText)
                    textColor(Color.GRAY)
                    onTextChanged { email -> store.dispatchAction(EmailChanged(email.toString())) }
                }

                editText {
                    width(dip(150))
                    margin(dip(12))
                    hint("Password")
                    text(store.getState().passwordText)
                    textColor(Color.GRAY)
                    onTextChanged {
                        password ->
                        store.dispatchAction(PasswordChanged(password.toString()))
                    }
                }

                button {
                    margin(dip(12))
                    padding(dip(12))
                    text("Login")
                    val enabled = store.getState().isLoginButtonEnabled()
                    backgroundColor(if (enabled) Color.BLUE else Color.GRAY)
                    enabled(enabled)
                    onClick { store.dispatchAction(LoginAction.LoginPressed) }
                }
            }
        }
    }

}
