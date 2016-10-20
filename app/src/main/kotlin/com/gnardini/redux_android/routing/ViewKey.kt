package com.gnardini.redux_android.routing

import android.view.View

enum class ViewKey(val viewFactory: (ViewFactory, Router) -> View) {
    LOGIN(ViewFactory::loginView),
    PICK_FRIENDS(ViewFactory::pickFriendsView)
}
