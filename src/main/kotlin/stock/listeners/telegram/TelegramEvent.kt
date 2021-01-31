package stock.listeners.telegram

import stock.listeners.EventListener

interface TelegramEvent<T>: EventListener<T> {
    fun accept(event: T): Boolean
}