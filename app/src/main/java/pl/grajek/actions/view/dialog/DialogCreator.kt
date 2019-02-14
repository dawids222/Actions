package pl.grajek.actions.view.dialog

import android.content.Context
import android.support.v7.app.AlertDialog

class DialogCreator(val context: Context) {

    fun create(
        title: Int, message: Int, isCancelable: Boolean,
        positiveText: Int, positiveCallback: () -> Unit,
        negativeText: Int, negativeCallback: () -> Unit
    ): AlertDialog {
        val builder = AlertDialog.Builder(context)

        builder
            .setTitle(title)
            .setMessage(message)
            .setCancelable(isCancelable)
            .setPositiveButton(positiveText) { _, _ ->
                positiveCallback()
            }
            .setNegativeButton(negativeText) { _, _ ->
                negativeCallback()
            }

        return builder.create()
    }
}