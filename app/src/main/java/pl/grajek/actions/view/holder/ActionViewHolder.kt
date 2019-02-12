package pl.grajek.actions.view.holder

import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.View
import pl.grajek.actions.R
import pl.grajek.actions.model.entity.Action

class ActionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val dateTextView: AppCompatTextView = view.findViewById(R.id.dateView)
    val amountTextView: AppCompatTextView = view.findViewById(R.id.amountView)
    val removeButton: AppCompatImageButton = view.findViewById(R.id.removeButton)

    var tag: Action? = null

    fun setOnClickListener(onItemClick: (Action) -> Unit) {
        itemView.setOnClickListener {
            val action = tag as Action
            onItemClick(action)
        }
    }
}