package pl.grajek.actions.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import pl.grajek.actions.R
import pl.grajek.actions.model.SingleLiveEvent
import pl.grajek.actions.model.entity.Category
import pl.grajek.actions.model.repository.CategoryRepository

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    val name = MutableLiveData<String>()
    val unit = MutableLiveData<String>()

    val errorMessage = SingleLiveEvent<Int>()
    val goBack = SingleLiveEvent<Boolean>()

    private val categoryRepository = CategoryRepository(application)
    private val categories = categoryRepository.select()


    fun insert(category: Category) = categoryRepository.insert(category)
    fun update(category: Category) = categoryRepository.update(category)

    fun confirm() {
        if (validate()) {
            addNewCategory()
            goBack.value = true
        } else {
            errorMessage.value = R.string.category_not_valid_error
        }
    }

    private fun validate(): Boolean {
        val name = this.name.value
        val unit = this.unit.value

        return if (name != null && unit != null)
            (name.isNotEmpty() && unit.isNotEmpty())
        else
            false
    }

    private fun addNewCategory() {
        val name = this.name.value
        val unit = this.unit.value

        val newCategory = Category()
        newCategory.let {
            it.name = name!!
            it.unit = unit!!
        }

        insert(newCategory)
    }
}