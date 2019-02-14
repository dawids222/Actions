package pl.grajek.actions.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import pl.grajek.actions.R
import pl.grajek.actions.model.SingleLiveEvent
import pl.grajek.actions.model.entity.Action
import pl.grajek.actions.model.repository.ActionRepository
import pl.grajek.actions.util.dateToMilliseconds
import java.util.*

class ActionViewModel(application: Application) : AndroidViewModel(application) {

    private val actionRepository = ActionRepository(application)

    val date = MutableLiveData<String>()
    val amount = MutableLiveData<String>()
    var categoryId = -1L
    private var modifyAction: Action? = null

    val errorMessage = SingleLiveEvent<Int>()
    val goBack = SingleLiveEvent<Boolean>()


    fun insert(action: Action) = actionRepository.insert(action)
    fun update(action: Action) = actionRepository.update(action)


    fun setActionToModify(action: Action) {
        modifyAction = action
        date.value = pl.grajek.actions.model.Date(action.date.time).stringify
        amount.value = action.quantity.toString()
    }

    fun confirm() {
        if (validate()) {
            val date = this.date.value!!
            val amount = this.amount.value?.toDouble()!!

            if (modifyAction != null)
                modifyAction(date, amount)
            else
                addNewAction(date, amount)

            goBack.value = true
        } else {
            errorMessage.value = R.string.category_not_valid_error
        }
    }

    private fun validate(): Boolean {
        val date = this.date.value
        val amount = this.amount.value?.toDouble()

        return if (date != null && amount != null)
            (date.isNotEmpty() && amount != 0.0)
        else
            false
    }

    private fun modifyAction(date: String, amount: Double) {
        modifyAction?.date = Date(dateToMilliseconds(date))
        modifyAction?.quantity = amount

        update(modifyAction!!)
    }

    private fun addNewAction(date: String, amount: Double) {
        val newAction = Action()
        newAction.let {
            it.date = Date(dateToMilliseconds(date))
            it.quantity = amount
            it.categoryId = categoryId
        }

        insert(newAction)
    }
}