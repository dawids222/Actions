package pl.grajek.actions.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(
    tableName = "action_table",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = CASCADE
    )]
)
data class Action(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var date: Date,
    var quantity: Double,
    var categoryId: Long
) : Serializable {

    constructor() : this(null, Date(), 0.0, -1)
}