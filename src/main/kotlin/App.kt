import stock.adapter.YahooFinanceAdapter
import stock.listeners.telegram.TelegramDailySummaryEvent
import stock.listeners.telegram.TelegramEventListener
import stock.processor.Event
import stock.processor.YahooStockProcessor
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.util.*
import kotlin.concurrent.schedule

class App(
    private val clock: Clock,
    private val processor: YahooStockProcessor,
    private val pollingFrequency: Duration
) {
    fun run() {
        Timer().schedule(0, pollingFrequency.toMillis()) {
            println(clock.instant())
            processor.process(Event(clock.instant()))
        }
    }
}

fun main() {
    val clock = Clock.systemUTC()
    val telegramDailySummaryEvent = TelegramDailySummaryEvent(Instant.now(), Duration.ofSeconds(5))
    val telegramEventListener = TelegramEventListener(listOf(telegramDailySummaryEvent))
    val yahooFinanceAdaptor = YahooFinanceAdapter()
    val processor = YahooStockProcessor(listOf("GME"), listOf(telegramEventListener), yahooFinanceAdaptor)
    val app = App(clock, processor, Duration.ofSeconds(2))
    app.run()
}