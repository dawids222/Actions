package pl.grajek.actions.util

import android.content.Context
import android.content.res.Configuration

fun dateToMilliseconds(date: String): Long {
    val date = sdf.parse(date)
    return date.time
}

fun isPortraitMode(context: Context): Boolean {
    return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}
