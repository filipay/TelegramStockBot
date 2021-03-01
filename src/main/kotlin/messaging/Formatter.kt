package messaging

import exchanges.Ticker

interface Formatter<T>  {
    fun format(payload: T): String
}

class NoOpFormatter: Formatter<String> {
    override fun format(payload: String): String = payload
}

class TickerFormatter: Formatter<Ticker> {
    override fun format(payload: Ticker): String =
        """
            *__${payload.name}__*
            
            _Price_: ${payload.price}
            _Ask_: ${payload.ask}
            _Bid_: ${payload.bid}
            _Day High_: ${payload.high}
            _Day Low_: ${payload.low}
        """
            .trimIndent()
            .trim()
            .replace(".", "\\.")
}