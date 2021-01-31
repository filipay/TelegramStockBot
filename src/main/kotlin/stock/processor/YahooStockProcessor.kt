package stock.processor

import stock.adapter.YahooFinanceAdaptor
import stock.listeners.EventListener

class YahooStockProcessor(
    private val listeners: List<EventListener<StockEvent>>,
    private val yahooFinanceAdaptor: YahooFinanceAdaptor
) : Processor {

    override fun process(event: Event) {
        val stock = yahooFinanceAdaptor.stock("GME")
        listeners.forEach { it.onEvent(StockEvent(stock, event.instant)) }
    }
}