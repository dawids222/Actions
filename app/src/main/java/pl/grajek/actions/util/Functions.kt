package pl.grajek.actions.util

fun dateToMilliseconds(date: String): Long {
    val date = sdf.parse(date)
    return date.time
}
