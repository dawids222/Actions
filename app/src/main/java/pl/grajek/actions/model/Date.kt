package pl.grajek.actions.model

import pl.grajek.actions.util.hdf
import pl.grajek.actions.util.sdf
import java.util.Date

class Date(milliseconds: Long) {

    val year: Int
    val month: Int
    val day: Int
    val stringify: String
    val longify: Long

    init {
        longify = milliseconds
        stringify = sdf.format(Date(milliseconds)).toString()
        val date = stringify.split('/')
        year = date[2].toInt()
        month = date[1].toInt() - 1
        day = date[0].toInt()
    }

    constructor() : this(System.currentTimeMillis())

    companion object {
        fun parseFromTime(time: String): Date {
            val date = pl.grajek.actions.model.Date()
            val timeString = "${date.day}/${date.month + 1}/${date.year} $time"
            return hdf.parse(timeString)
        }
    }
}