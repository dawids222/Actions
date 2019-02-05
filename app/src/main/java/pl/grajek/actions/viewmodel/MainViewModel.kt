package pl.grajek.actions.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import pl.grajek.actions.model.repository.ActionRepository
import pl.grajek.actions.model.repository.CategoryRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val actionRepository = ActionRepository(application)
    val categoryRepository = CategoryRepository(application)
}