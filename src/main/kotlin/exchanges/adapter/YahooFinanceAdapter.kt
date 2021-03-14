package exchanges.adapter

import exchanges.Mapper
import exchanges.Ticker
import yahoofinance.Stock
import yahoofinance.YahooFinance

class YahooFinanceAdapter(private val mapper: Mapper<Stock, Ticker>) : TickerAdapter {
    override fun ticker(symbol: String): Ticker = mapper.map(YahooFinance.get(symbol))
    override fun tickers(symbols: List<String>): List<Ticker> =
        YahooFinance.get(symbols.toTypedArray())
            .values
            .map { mapper.map(it) }
}