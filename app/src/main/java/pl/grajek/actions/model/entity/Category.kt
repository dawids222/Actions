package pl.grajek.actions.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var name: String,
    var unit: String
) {

    constructor() : this(null, "", "")
}