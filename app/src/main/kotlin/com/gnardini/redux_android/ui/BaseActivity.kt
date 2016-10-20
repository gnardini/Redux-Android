package com.gnardini.redux_android.ui

import android.R
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.gnardini.redux_android.routing.ViewContainer

class BaseActivity: AppCompatActivity(), ViewContainer {

    lateinit var goBack: () -> Unit

    override val context = this

    override fun setOnBackPressListener(goBack: () -> Unit) {
        this.goBack = goBack
    }

    override fun drawView(view: View) {
        val container = findViewById(R.id.content) as ViewGroup
        container.removeAllViews()
        container.addView(view)
    }

    override fun onBackPressed() {
        goBack()
    }

    override fun finish() = super.finish()

}
