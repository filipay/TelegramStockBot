package exchanges.processor

import exchanges.Event
import exchanges.TickerEvent
import exchanges.adapter.YahooFinanceAdapter
import exchanges.dispatchers.EventDispatcher
import org.apache.logging.log4j.LogManager

class YahooStockProcessor(
    private val stocks: List<String>,
    private val dispatchers: List<EventDispatcher<TickerEvent>>,
    private val yahooFinanceAdapter: YahooFinanceAdapter
) : Processor {
    private val logger = LogManager.getLogger(YahooStockProcessor::class.java)
    override fun process(event: Event) {
        logger.info("Processing Yahoo event", event)
        val stocks = yahooFinanceAdapter.stocks(stocks)
        dispatchers.forEach { dispatcher ->
            stocks.values.forEach {
                dispatcher.onEvent(TickerEvent(it, event.instant))
            }
        }
    }
}