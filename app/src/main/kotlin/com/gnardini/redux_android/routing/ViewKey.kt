package com.gnardini.redux_android.routing

import android.view.View

enum class ViewKey(val viewFactory: (Router) -> View) {
    LOGIN(Router::loginView),
    PICK_FRIENDS(Router::pickFriendsView)
}
