package pl.grajek.actions.model.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import pl.grajek.actions.model.dao.CategoryDao
import pl.grajek.actions.model.database.AppDatabase
import pl.grajek.actions.model.entity.Category

class CategoryRepository(application: Application) {

    private val categoryDao: CategoryDao
    private val categories: LiveData<MutableList<Category>>

    init {
        val database = AppDatabase.getInstance(application)
        categoryDao = database.categoryDao()
        categories = categoryDao.selectAll()
    }

    fun insert(category: Category) {
        InsertCategoryAsyncTask(categoryDao).execute(category)
    }

    fun update(category: Category) {
        UpdateCategoryAsyncTask(categoryDao).execute(category)
    }

    fun delete(category: Category) {
        DeleteCategoryAsyncTask(categoryDao).execute(category)
    }

    fun select(): LiveData<MutableList<Category>> {
        return categories
    }

    private class InsertCategoryAsyncTask(val categoryDao: CategoryDao) : AsyncTask<Category, Unit, Unit>() {
        override fun doInBackground(vararg params: Category?) {
            categoryDao.insert(params[0]!!)
        }
    }

    private class UpdateCategoryAsyncTask(val categoryDao: CategoryDao) : AsyncTask<Category, Unit, Unit>() {
        override fun doInBackground(vararg params: Category?) {
            categoryDao.update(params[0]!!)
        }
    }

    private class DeleteCategoryAsyncTask(val categoryDao: CategoryDao) : AsyncTask<Category, Unit, Unit>() {
        override fun doInBackground(vararg params: Category?) {
            categoryDao.delete(params[0]!!)
        }
    }
}