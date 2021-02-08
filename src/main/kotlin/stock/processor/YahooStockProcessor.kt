package stock.processor

import stock.adapter.YahooFinanceAdapter
import stock.dispatchers.EventDispatcher

class YahooStockProcessor(
    private val stocks: List<String>,
    private val dispatchers: List<EventDispatcher<StockEvent>>,
    private val yahooFinanceAdapter: YahooFinanceAdapter
) : Processor {

    override fun process(event: Event) {
        val stocks = yahooFinanceAdapter.stocks(stocks)
        dispatchers.forEach { listener ->
            stocks.values.forEach {
                listener.onEvent(StockEvent(it, event.instant))
            }
        }
    }
}