package exchanges.adapter

import exchanges.Mapper
import exchanges.Ticker
import yahoofinance.Stock
import yahoofinance.YahooFinance

class YahooFinanceAdapter(private val mapper: Mapper<Stock, Ticker>) {
    fun stock(stockName: String): Ticker = mapper.map(YahooFinance.get(stockName))
    fun stocks(stockNames: List<String>): Map<String, Ticker> =
        YahooFinance.get(stockNames.toTypedArray())
            .map { Pair(it.key, mapper.map(it.value)) }
            .toMap()
}