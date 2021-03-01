package exchanges

import java.time.Instant

data class TickerEvent(val ticker: Ticker, val instant: Instant)

data class Event(val instant: Instant)

data class Ticker(
    val name: String,
    val price: Double,
    val ask: Double,
    val bid: Double,
    val high: Double,
    val low: Double,
    val volume: Long,
)