package pl.grajek.actions.model

import android.support.design.widget.Snackbar
import android.view.View

class SnackbarProvider {

    fun create(
        view: View, stringResource: Int, short: Boolean, color: Int? = null
    ): Snackbar {
        val duration = if (short) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
        val snackbar = Snackbar.make(view, stringResource, duration)

        color?.let {
            val v = snackbar.view
            v.setBackgroundColor(color)
        }

        return snackbar
    }
}