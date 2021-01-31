package stock.processor

import yahoofinance.Stock
import java.time.Instant

data class StockEvent(val stock: Stock, val instant: Instant)

data class Event(val instant: Instant)
