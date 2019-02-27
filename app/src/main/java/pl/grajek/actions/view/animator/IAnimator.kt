package pl.grajek.actions.view.animator

import android.view.View

interface IAnimator {

    fun animate(view: View) {
        animate(view, 1000)
    }

    fun animate(view: View, duration: Long)
}