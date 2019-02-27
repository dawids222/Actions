package pl.grajek.actions.view.animator

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

class FadeAnimator : IAnimator {

    private var onAnimationEndListener: (animation: Animation?) -> Unit = {}

    override fun animate(view: View, duration: Long) {
        view.visibility = View.VISIBLE

        val fade = AlphaAnimation(1f, 0f)
        fade.duration = duration
        fade.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                onAnimationEndListener(animation)
                view.visibility = View.GONE
            }
        })

        view.startAnimation(fade)
    }

    fun setOnEndListener(listener: (animation: Animation?) -> Unit): FadeAnimator {
        onAnimationEndListener = listener
        return this
    }
}