package stock.dispatchers.telegram

import stock.dispatchers.EventDispatcher

interface ConditionalEventDispatcher<T>: EventDispatcher<T> {
    fun accept(event: T): Boolean
}