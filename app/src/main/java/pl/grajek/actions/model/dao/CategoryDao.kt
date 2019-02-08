package pl.grajek.actions.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import pl.grajek.actions.model.entity.Category

@Dao
interface CategoryDao {

    @Insert
    fun insert(category: Category)

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)

    @Query("DELETE FROM category_table WHERE id = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM category_table")
    fun selectAll(): LiveData<MutableList<Category>>
}