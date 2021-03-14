package exchanges.adapter

import exchanges.Ticker

interface TickerAdapter {
    fun ticker(symbol: String): Ticker
    fun tickers(symbols: List<String>): List<Ticker>
}