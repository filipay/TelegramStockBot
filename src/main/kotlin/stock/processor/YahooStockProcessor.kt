package stock.processor

import stock.adapter.YahooFinanceAdapter
import stock.listeners.EventListener

class YahooStockProcessor(
    private val stocks: List<String>,
    private val listeners: List<EventListener<StockEvent>>,
    private val yahooFinanceAdapter: YahooFinanceAdapter
) : Processor {

    override fun process(event: Event) {
        val stocks = yahooFinanceAdapter.stocks(stocks)
        listeners.forEach { listener ->
            stocks.values.forEach {
                listener.onEvent(StockEvent(it, event.instant))
            }
        }
    }
}