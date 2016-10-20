package com.gnardini.redux_android.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle

interface ActivityLifecycleCallbacksWrapper: Application.ActivityLifecycleCallbacks {

    override fun onActivityStarted(activity: Activity?) {}

    override fun onActivityStopped(activity: Activity?) {}

    override fun onActivityResumed(activity: Activity?) {}

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

    override fun onActivityDestroyed(activity: Activity?) {}

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}

    override fun onActivityPaused(activity: Activity?) {}

}