package pl.grajek.actions.util

import java.text.SimpleDateFormat

fun dateToMilliseconds(date: String): Long {
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    val date = sdf.parse(date)
    return date.time
}
