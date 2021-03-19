package exchanges.processor

import exchanges.Event
import exchanges.TickerEvent
import exchanges.adapter.TickerAdapter
import exchanges.dispatchers.EventDispatcher
import org.apache.logging.log4j.LogManager

class TickerProcessor(
    private val symbols: List<String>,
    private val dispatchers: List<EventDispatcher<TickerEvent>>,
    private val tickerAdapter: TickerAdapter,
) : Processor {
    private val logger = LogManager.getLogger(TickerProcessor::class.java)

    override suspend fun process(event: Event) {
        logger.info("Processing event with ${tickerAdapter::class.java}", event)
        val tickers = tickerAdapter.tickers(symbols)
        dispatchers.forEach { dispatcher ->
            tickers.forEach {
                dispatcher.onEvent(TickerEvent(it, event.instant))
            }
        }
    }
}