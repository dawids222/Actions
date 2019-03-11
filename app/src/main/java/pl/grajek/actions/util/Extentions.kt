package pl.grajek.actions.util

import android.content.SharedPreferences
import android.support.design.widget.TabLayout
import java.util.*

fun TabLayout.next() {
    val tab = getTabAt(selectedTabPosition + 1)
    tab?.select()
}

fun TabLayout.previous() {
    val tab = getTabAt(selectedTabPosition - 1)
    tab?.select()
}

fun Date.now(): Date {
    val milliseconds = System.currentTimeMillis()
    return Date(milliseconds)
}

fun Date.add(milliseconds: Long): Date {
    val time = this.time + milliseconds
    return Date(time)
}

fun SharedPreferences.edit(callback: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    callback(editor)
    editor.apply()
}