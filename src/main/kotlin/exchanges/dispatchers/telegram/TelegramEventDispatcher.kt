package exchanges.dispatchers.telegram

import exchanges.TickerEvent
import exchanges.dispatchers.EventDispatcher
import ifTrue

class TelegramEventDispatcher(
    private val eventDispatchers: List<ConditionalEventDispatcher<TickerEvent>>
): EventDispatcher<TickerEvent> {
    override fun onEvent(event: TickerEvent) = eventDispatchers.forEach {
        it.accept(event).ifTrue { it.onEvent(event) }
    }
}