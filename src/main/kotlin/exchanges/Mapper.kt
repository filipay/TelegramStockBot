package exchanges

import yahoofinance.Stock
import org.knowm.xchange.dto.marketdata.Ticker as KrakenTicker


interface Mapper<I,O> {
    fun map(result: I) : O
}

class KrakenMapper : Mapper<KrakenTicker, Ticker> {
    override fun map(result: KrakenTicker): Ticker =
        Ticker(
            result.instrument.toString(),
            result.last.toDouble(),
            result.ask.toDouble(),
            result.bid.toDouble(),
            result.high.toDouble(),
            result.low.toDouble(),
            result.volume.toLong()
        )
}

class YahooFinanceMapper : Mapper<Stock, Ticker> {
    override fun map(result: Stock): Ticker =
        Ticker(
            result.name,
            result.quote.price.toDouble(),
            result.quote.ask.toDouble(),
            result.quote.bid.toDouble(),
            result.quote.dayHigh.toDouble(),
            result.quote.dayLow.toDouble(),
            result.quote.volume
        )
}