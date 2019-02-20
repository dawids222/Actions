package pl.grajek.actions.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import pl.grajek.actions.R
import pl.grajek.actions.model.SingleLiveEvent
import pl.grajek.actions.model.dto.ActivityStartModel
import pl.grajek.actions.model.entity.Action
import pl.grajek.actions.model.entity.Category
import pl.grajek.actions.model.repository.ActionRepository
import pl.grajek.actions.model.repository.CategoryRepository
import pl.grajek.actions.view.activity.ActionActivity
import pl.grajek.actions.view.activity.CategoryActivity
import pl.grajek.actions.view.activity.GraphActivity

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val actionRepository = ActionRepository(application)
    private val categoryRepository = CategoryRepository(application)

    val activityToStart = SingleLiveEvent<ActivityStartModel>()
    var currentCategory: Category? = null

    val errorMessage = SingleLiveEvent<Int>()

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

    fun gotoActionActivity(action: Action? = null) {
        val bundle = Bundle()
        bundle.putSerializable(ActionActivity.ACTIONS, currentActions?.value?.toTypedArray())
        if (currentCategory != null) {
            if (action != null) { // modyfikacja
                bundle.putSerializable(ActionActivity.ACTION, action)
            } else { // dodanie nowego
                bundle.putLong(ActionActivity.CATEGORY_ID, currentCategory!!.id!!)
            }
        }
        activityToStart.value = ActivityStartModel(
            ActionActivity::class.java, bundle
        )
    }

    fun gotoGraphActivity() {
        if (currentCategory != null && currentActions?.value?.isNotEmpty() == true) {
            val bundle = Bundle()
            bundle.putSerializable(GraphActivity.CATEGORY, currentCategory)
            activityToStart.value = ActivityStartModel(
                GraphActivity::class.java, bundle
            )
        } else {
            errorMessage.value = R.string.category_null_or_empty
        }
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
    fun delete(action: Action) = actionRepository.delete(action)
    private fun selectActions(categoryId: Long): LiveData<MutableList<Action>> = actionRepository.selectDesc(categoryId)
}