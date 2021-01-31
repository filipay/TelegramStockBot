package stock.adapter

import yahoofinance.Stock
import yahoofinance.YahooFinance

class YahooFinanceAdapter {
    fun stock(stockName: String): Stock = YahooFinance.get(stockName)
    fun stocks(stockNames: List<String>): Map<String, Stock> = YahooFinance.get(stockNames.toTypedArray())
}