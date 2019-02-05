package pl.grajek.actions.model.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import pl.grajek.actions.model.dao.ActionDao
import pl.grajek.actions.model.database.AppDatabase
import pl.grajek.actions.model.entity.Action

class ActionRepository(application: Application) {

    private val actionDao: ActionDao
    private val actions: LiveData<MutableList<Action>>

    init {
        val database = AppDatabase.getInstance(application)
        actionDao = database.actionDao()
        actions = actionDao.selectAll()
    }

    fun insert(action: Action) {
        InsertActionAsyncTask(actionDao).execute(action)
    }

    fun update(action: Action) {
        UpdateActionAsyncTask(actionDao).execute(action)
    }

    fun delete(action: Action) {
        DeleteActionAsyncTask(actionDao).execute(action)
    }

    fun selectAll(): LiveData<MutableList<Action>> {
        return actions
    }

    companion object {
        private class InsertActionAsyncTask(val actionDao: ActionDao) : AsyncTask<Action, Unit, Unit>() {
            override fun doInBackground(vararg params: Action?) {
                actionDao.insert(params[0]!!)
            }
        }

        private class UpdateActionAsyncTask(val actionDao: ActionDao) : AsyncTask<Action, Unit, Unit>() {
            override fun doInBackground(vararg params: Action?) {
                actionDao.update(params[0]!!)
            }
        }

        private class DeleteActionAsyncTask(val actionDao: ActionDao) : AsyncTask<Action, Unit, Unit>() {
            override fun doInBackground(vararg params: Action?) {
                actionDao.delete(params[0]!!)
            }
        }
    }
}