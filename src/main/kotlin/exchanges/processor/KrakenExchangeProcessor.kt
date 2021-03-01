package exchanges.processor

import exchanges.Event
import exchanges.TickerEvent
import exchanges.adapter.KrakenExchangeAdapter
import exchanges.dispatchers.EventDispatcher

class KrakenExchangeProcessor(
    private val cryptos: List<String>,
    private val dispatchers: List<EventDispatcher<TickerEvent>>,
    private val krakenExchangeAdapter: KrakenExchangeAdapter
) : Processor {
    override fun process(event: Event) {
        val cryptos = krakenExchangeAdapter.cryptos(cryptos)
        dispatchers.forEach { dispatcher ->
            cryptos.forEach {
                dispatcher.onEvent(TickerEvent(it, event.instant))
            }
        }
    }
}