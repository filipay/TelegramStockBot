package exchanges.processor

import exchanges.Event
import exchanges.TickerEvent
import exchanges.adapter.KrakenExchangeAdapter
import exchanges.dispatchers.EventDispatcher
import org.apache.logging.log4j.LogManager

class KrakenExchangeProcessor(
    private val cryptos: List<String>,
    private val dispatchers: List<EventDispatcher<TickerEvent>>,
    private val krakenExchangeAdapter: KrakenExchangeAdapter
) : Processor {
    private val logger = LogManager.getLogger(KrakenExchangeProcessor::class.java)
    override fun process(event: Event) {
        logger.info("Processing Kraken event", event)
        val cryptos = krakenExchangeAdapter.cryptos(cryptos)
        dispatchers.forEach { dispatcher ->
            cryptos.forEach {
                dispatcher.onEvent(TickerEvent(it, event.instant))
            }
        }
    }
}