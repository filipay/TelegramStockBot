package stock.listeners.telegram

import ifTrue
import stock.listeners.EventListener
import stock.processor.StockEvent

class TelegramEventListener(
    private val telegramEventListeners: List<TelegramEvent<StockEvent>>
): EventListener<StockEvent> {
    override fun onEvent(event: StockEvent) = telegramEventListeners.forEach {
        it.accept(event).ifTrue { it.onEvent(event) }
    }
}