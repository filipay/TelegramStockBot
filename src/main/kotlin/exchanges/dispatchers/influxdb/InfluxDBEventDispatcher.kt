package exchanges.dispatchers.influxdb

import com.influxdb.client.InfluxDBClient
import exchanges.TickerEvent
import exchanges.dispatchers.EventDispatcher
import use

class InfluxDBEventDispatcher(private val influxDBClient: InfluxDBClient): EventDispatcher<TickerEvent> {
    override fun onEvent(event: TickerEvent) {
        influxDBClient.writeApi.use { writeApi ->
            event.ticker.toMeasurements(event.instant).forEach { writeApi.writePoint(it) }
        }
    }
}