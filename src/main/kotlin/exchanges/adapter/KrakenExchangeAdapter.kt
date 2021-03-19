package exchanges.adapter

import exchanges.Mapper
import exchanges.Ticker
import org.knowm.xchange.Exchange
import org.knowm.xchange.currency.CurrencyPair
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam
import org.knowm.xchange.dto.marketdata.Ticker as KrakenTicker

class KrakenExchangeAdapter(private val client: Exchange, private val mapper: Mapper<KrakenTicker, Ticker>) : TickerAdapter {
    override fun tickers(symbols: List<String>) : List<Ticker> = runCatching {
        client
            .marketDataService
            .getTickers(CurrencyPairsParam { symbols.map { CurrencyPair(it, "EUR") } })
            .map { mapper.map(it) }
    }.getOrDefault(listOf())

    override fun ticker(symbol: String): Ticker =
        client
            .marketDataService
            .getTicker(CurrencyPair(symbol, "EUR"))
            .let { mapper.map(it) }
}

