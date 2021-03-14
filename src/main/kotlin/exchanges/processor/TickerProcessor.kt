package exchanges.processor

import com.influxdb.client.InfluxDBClient
import exchanges.Event
import exchanges.TickerEvent
import exchanges.adapter.TickerAdapter
import exchanges.dispatchers.EventDispatcher
import org.apache.logging.log4j.LogManager
import use

class TickerProcessor(
    private val symbols: List<String>,
    private val dispatchers: List<EventDispatcher<TickerEvent>>,
    private val tickerAdapter: TickerAdapter,
    private val influxDBClient: InfluxDBClient
) : Processor {
    private val logger = LogManager.getLogger(TickerProcessor::class.java)

    override fun process(event: Event) {
        logger.info("Processing event with ${tickerAdapter::class}", event)
        val tickers = tickerAdapter.tickers(symbols)
        influxDBClient.writeApi.use { writeApi ->
            tickers.forEach {
                it.toMeasurements().forEach { measurement -> writeApi.writePoint(measurement) }
            }
        }
        dispatchers.forEach { dispatcher ->
            tickers.forEach {
                dispatcher.onEvent(TickerEvent(it, event.instant))
            }
        }
    }
}