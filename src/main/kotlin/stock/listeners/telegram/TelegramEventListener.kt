package stock.listeners.telegram

import stock.listeners.EventListener
import stock.processor.StockEvent

class TelegramEventListener(
    private val telegramEventListeners: List<TelegramEvent<StockEvent>>
): EventListener<StockEvent> {
    override fun onEvent(event: StockEvent) = telegramEventListeners.forEach {
        if (it.accept(event)) { it.onEvent(event) }
    }
}