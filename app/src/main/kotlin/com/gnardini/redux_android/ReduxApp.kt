package com.gnardini.redux_android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.gnardini.redux_android.extension.ActivityLifecycleCallbacksWrapper
import com.gnardini.redux_android.injector.NetworkInjector
import com.gnardini.redux_android.routing.Router
import com.gnardini.redux_android.routing.ViewContainer

class ReduxApp : Application() {

    val networkInjector by lazy {
        NetworkInjector()
    }

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(object: ActivityLifecycleCallbacksWrapper {

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                val router = Router(activity as ViewContainer, networkInjector)
                router.bindOnBackListener()
                router.showInitialView()
            }

        })
    }

}
