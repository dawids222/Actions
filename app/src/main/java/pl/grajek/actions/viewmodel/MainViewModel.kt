package pl.grajek.actions.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import pl.grajek.actions.model.entity.Action
import pl.grajek.actions.model.entity.Category
import pl.grajek.actions.model.repository.ActionRepository
import pl.grajek.actions.model.repository.CategoryRepository
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val actionRepository = ActionRepository(application)
    private val categoryRepository = CategoryRepository(application)


    fun insert(category: Category) = categoryRepository.insert(category)
    fun update(category: Category) = categoryRepository.update(category)
    fun dalete(category: Category) = categoryRepository.delete(category)
    fun selectCategories(): LiveData<MutableList<Category>> = categoryRepository.select()

    fun insert(action: Action) = actionRepository.insert(action)
    fun update(action: Action) = actionRepository.update(action)
    fun dalete(action: Action) = actionRepository.delete(action)
    fun selectActions(): LiveData<MutableList<Action>> = actionRepository.select()
    fun selectActions(categoryId: Long): LiveData<MutableList<Action>> = actionRepository.select(categoryId)
    fun selectActions(from: Date, to: Date): LiveData<MutableList<Action>> = actionRepository.select(from, to)
}