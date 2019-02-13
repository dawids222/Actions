package pl.grajek.actions.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import pl.grajek.actions.R
import pl.grajek.actions.model.Date
import pl.grajek.actions.model.entity.Action
import pl.grajek.actions.view.holder.ActionViewHolder

class ActionAdapter(
    private val onItemClick: (Action) -> Unit,
    val onRemoveButtonClick: (Action) -> Unit
) : RecyclerView.Adapter<ActionViewHolder>() {

    private var actions = mutableListOf<Action>()
    private var recyclerView: RecyclerView? = null

    fun setActions(actions: MutableList<Action>) {
        this.actions = actions
        notifyDataSetChanged()
        recyclerView?.scheduleLayoutAnimation()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.action_item, parent, false)

        return ActionViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return actions.size
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        val action = actions[position]
        holder.dateTextView.text = Date(action.date.time).stringify
        holder.amountTextView.text = action.quantity.toString()
        holder.tag = action
        holder.setOnClickListener(onItemClick)
        holder.removeButton.setOnClickListener {
            onRemoveButtonClick(holder.tag as Action)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }
}