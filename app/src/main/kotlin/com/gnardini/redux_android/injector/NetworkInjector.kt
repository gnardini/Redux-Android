package com.gnardini.redux_android.injector

import com.gnardini.redux_android.repository.UsersRepository

class NetworkInjector {

    val usersRepository by lazy {
        UsersRepository()
    }

}