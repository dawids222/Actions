package pl.grajek.actions.view.holder

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.View
import pl.grajek.actions.R
import pl.grajek.actions.model.entity.Action

class ActionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val dateTextView: AppCompatTextView = view.findViewById(R.id.dateView)
    val amountTextView: AppCompatTextView = view.findViewById(R.id.amountView)

    var tag: Action? = null
}