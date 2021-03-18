package exchanges

import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import java.time.Instant

data class TickerEvent(val ticker: Ticker, val instant: Instant)

data class Event(val instant: Instant)

data class Ticker(
    val name: String,
    val symbol: String,
    val price: Double,
    val ask: Double,
    val bid: Double,
    val high: Double,
    val low: Double,
    val volume: Long
) {
    fun toMeasurements(timestamp: Instant): List<Point> = listOf(
        Point(symbol).addField("ask", ask),
        Point(symbol).addField("bid", bid),
        Point(symbol).addField("price", price),
        Point(symbol).addField("volume", volume)
    ).map { it.time(timestamp, WritePrecision.S) }
}