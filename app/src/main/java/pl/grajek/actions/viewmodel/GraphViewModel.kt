package pl.grajek.actions.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import pl.grajek.actions.model.repository.ActionRepository

class GraphViewModel(application: Application) : AndroidViewModel(application) {

    private val actionRepository = ActionRepository(application)


    fun select(id: Long) = actionRepository.select(id)
}