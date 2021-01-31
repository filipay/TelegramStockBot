package stock.adapter

import yahoofinance.Stock
import yahoofinance.YahooFinance

class YahooFinanceAdaptor {
    fun stock(stockName: String): Stock = YahooFinance.get(stockName)
}