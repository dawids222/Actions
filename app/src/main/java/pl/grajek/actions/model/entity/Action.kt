package pl.grajek.actions.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "action_table")
class Action(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var date: Long, //todo: convert to string?
    var quantity: Double,
    var categoryId: Int
) {

    constructor() : this(null, Date().time, 0.0, -1)
}