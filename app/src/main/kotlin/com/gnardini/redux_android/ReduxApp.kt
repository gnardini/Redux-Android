package com.gnardini.redux_android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.gnardini.redux_android.injector.NetworkInjector
import com.gnardini.redux_android.routing.Router
import com.gnardini.redux_android.routing.ViewContainer
import com.gnardini.redux_android.routing.ViewFactory
import com.gnardini.redux_android.utils.ActivityLifecycleCallbacksWrapper

class ReduxApp : Application() {

    val networkInjector by lazy {
        NetworkInjector()
    }

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacksWrapper {

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                val viewFactory = ViewFactory(activity as ViewContainer, networkInjector)
                val router = Router(activity, viewFactory)
                router.bindOnBackListener()
                router.showInitialView()
            }

        })
    }

}
