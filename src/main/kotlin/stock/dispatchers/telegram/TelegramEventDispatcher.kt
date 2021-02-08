package stock.dispatchers.telegram

import ifTrue
import stock.dispatchers.EventDispatcher
import stock.processor.StockEvent

class TelegramEventDispatcher(
    private val eventDispatchers: List<ConditionalEventDispatcher<StockEvent>>
): EventDispatcher<StockEvent> {
    override fun onEvent(event: StockEvent) = eventDispatchers.forEach {
        it.accept(event).ifTrue { it.onEvent(event) }
    }
}