package pl.grajek.actions.model.notification

import java.util.concurrent.atomic.AtomicInteger

object NotificationID {

    private val c = AtomicInteger(0)

    fun get(): Int {
        return c.incrementAndGet()
    }
}