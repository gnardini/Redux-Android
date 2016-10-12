package com.gnardini.redux_android.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.gnardini.redux_android.routing.Router
import com.gnardini.redux_android.routing.ViewContainer

class BaseActivity: AppCompatActivity(), ViewContainer {

    override val context = this
    val router = Router()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router.initializeContainer(this)
    }

    override fun drawView(view: View) {
        val container = findViewById(android.R.id.content) as ViewGroup
        container.removeAllViews()
        container.addView(view)
    }

    override fun onBackPressed() {
        router.goBack()
    }

}
