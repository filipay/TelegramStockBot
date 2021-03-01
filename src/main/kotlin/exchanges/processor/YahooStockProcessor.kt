package exchanges.processor

import exchanges.Event
import exchanges.TickerEvent
import exchanges.adapter.YahooFinanceAdapter
import exchanges.dispatchers.EventDispatcher

class YahooStockProcessor(
    private val stocks: List<String>,
    private val dispatchers: List<EventDispatcher<TickerEvent>>,
    private val yahooFinanceAdapter: YahooFinanceAdapter
) : Processor {

    override fun process(event: Event) {
        val stocks = yahooFinanceAdapter.stocks(stocks)
        dispatchers.forEach { dispatcher ->
            stocks.values.forEach {
                dispatcher.onEvent(TickerEvent(it, event.instant))
            }
        }
    }
}