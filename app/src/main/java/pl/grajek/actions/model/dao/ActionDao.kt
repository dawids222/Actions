package pl.grajek.actions.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import pl.grajek.actions.model.entity.Action
import java.util.*

@Dao
interface ActionDao {

    @Insert
    fun insert(action: Action)

    @Update
    fun update(action: Action)

    @Delete
    fun delete(action: Action)

    @Query("SELECT * FROM action_table")
    fun select(): LiveData<MutableList<Action>>

    @Query("SELECT * FROM action_table WHERE date BETWEEN :from AND :to")
    fun select(from: Date, to: Date): LiveData<MutableList<Action>>

    @Query("SELECT * FROM action_table WHERE categoryId = :categoryId")
    fun select(categoryId: Long): LiveData<MutableList<Action>>
}