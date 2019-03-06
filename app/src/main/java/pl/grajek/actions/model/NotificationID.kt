package pl.grajek.actions.model

import java.util.concurrent.atomic.AtomicInteger

object NotificationID {

    private val c = AtomicInteger(0)

    fun get(): Int {
        return c.incrementAndGet()
    }
}