package pl.grajek.actions.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "category_table")
class Category(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    name: String,
    unit: String
) {

    constructor() : this(null, "", "")
}