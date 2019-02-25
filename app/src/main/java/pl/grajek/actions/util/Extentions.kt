package pl.grajek.actions.util

import android.support.design.widget.TabLayout

fun TabLayout.next() {
    val tab = getTabAt(selectedTabPosition + 1)
    tab?.select()
}

fun TabLayout.previous() {
    val tab = getTabAt(selectedTabPosition - 1)
    tab?.select()
}