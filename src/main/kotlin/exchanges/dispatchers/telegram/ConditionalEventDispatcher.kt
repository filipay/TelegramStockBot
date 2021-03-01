package exchanges.dispatchers.telegram

import exchanges.dispatchers.EventDispatcher

interface ConditionalEventDispatcher<T>: EventDispatcher<T> {
    fun accept(event: T): Boolean
}