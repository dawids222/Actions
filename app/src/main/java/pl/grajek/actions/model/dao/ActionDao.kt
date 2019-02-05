package pl.grajek.actions.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import pl.grajek.actions.model.entity.Action

@Dao
interface ActionDao {

    @Insert
    fun insert(action: Action)

    @Update
    fun update(action: Action)

    @Delete
    fun delete(action: Action)

    @Query("SELECT * FROM action_table")
    fun selectAll(): LiveData<MutableList<Action>>
}