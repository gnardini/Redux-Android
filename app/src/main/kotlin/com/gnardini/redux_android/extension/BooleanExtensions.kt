package com.gnardini.redux_android.extension

infix fun <T> Boolean.then(param: T): T? = if (this) param else null
