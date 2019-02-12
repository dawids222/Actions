package pl.grajek.actions.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import pl.grajek.actions.model.SingleLiveEvent
import pl.grajek.actions.model.dto.ActivityStartModel
import pl.grajek.actions.model.entity.Action
import pl.grajek.actions.model.entity.Category
import pl.grajek.actions.model.repository.ActionRepository
import pl.grajek.actions.model.repository.CategoryRepository
import pl.grajek.actions.view.activity.ActionActivity
import pl.grajek.actions.view.activity.CategoryActivity
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val actionRepository = ActionRepository(application)
    private val categoryRepository = CategoryRepository(application)

    val activityToStart = SingleLiveEvent<ActivityStartModel>()
    var currentCategory: Category? = null

    private var currentActions: LiveData<MutableList<Action>>? = null


    fun gotoCategoryActivity() {
        activityToStart.value = ActivityStartModel(
            CategoryActivity::class.java,
            Bundle()
        )
    }

    fun gotoCategoryEditActivity(category: Category) {
        val bundle = Bundle()
        bundle.putSerializable(CategoryActivity.CATEGORY, category)
        activityToStart.value = ActivityStartModel(
            CategoryActivity::class.java, bundle
        )
    }

    fun gotoActionActivity() {
        val bundle = Bundle()
        if (currentCategory != null)
            bundle.putLong(ActionActivity.CATEGORY_ID, currentCategory!!.id!!)
        activityToStart.value = ActivityStartModel(
            ActionActivity::class.java, bundle
        )
    }

    fun gotoActionEditActivity(action: Action) {
        val bundle = Bundle()
        if (currentCategory != null)
            bundle.putSerializable(ActionActivity.ACTION, action)
        activityToStart.value = ActivityStartModel(
            ActionActivity::class.java, bundle
        )
    }

    fun observeActions(owner: LifecycleOwner, id: Long, observer: Observer<MutableList<Action>>) {
        currentActions?.removeObservers(owner)
        currentActions = selectActions(id)
        currentActions?.observe(owner, observer)
    }

    fun insert(category: Category) = categoryRepository.insert(category)
    fun update(category: Category) = categoryRepository.update(category)
    fun delete(category: Category) = categoryRepository.delete(category)
    fun delete(id: Long) = categoryRepository.delete(id)
    fun selectCategories(): LiveData<MutableList<Category>> = categoryRepository.select()

    fun insert(action: Action) = actionRepository.insert(action)
    fun update(action: Action) = actionRepository.update(action)
    fun dalete(action: Action) = actionRepository.delete(action)
    fun selectActions(): LiveData<MutableList<Action>> = actionRepository.select()
    fun selectActions(categoryId: Long): LiveData<MutableList<Action>> = actionRepository.select(categoryId)
    fun selectActions(from: Date, to: Date): LiveData<MutableList<Action>> = actionRepository.select(from, to)
}