package pl.grajek.actions.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var name: String,
    var unit: String
) : Serializable {

    constructor() : this(null, "", "")
}