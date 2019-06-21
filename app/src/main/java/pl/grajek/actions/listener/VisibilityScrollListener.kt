package pl.grajek.actions.listener

import android.support.v7.widget.RecyclerView

class VisibilityScrollListener(
    private val onShow: () -> Unit,
    private val onHide: () -> Unit,
    private val threshold: Int = 5
) : RecyclerView.OnScrollListener() {
    private var show = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > threshold) {
            show = false
        } else if (dy < -threshold) {
            show = true
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (show) {
            onShow()
        } else {
            onHide()
        }
    }
}