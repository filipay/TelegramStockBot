import stock.adapter.YahooFinanceAdaptor
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
    private val processor: YahooStockProcessor
) {
    fun run() {
        Timer().schedule(0,500) {
            println(clock.instant())
            processor.process(Event(clock.instant()))
        }
    }
}

fun main() {
    val clock = Clock.systemUTC()
    val telegramDailySummaryEvent = TelegramDailySummaryEvent(Instant.now(), Duration.ofDays(1))
    val telegramEventListener = TelegramEventListener(listOf(telegramDailySummaryEvent))
    val yahooFinanceAdaptor = YahooFinanceAdaptor()
    val processor = YahooStockProcessor(listOf(telegramEventListener), yahooFinanceAdaptor)
    val app = App(clock, processor)
    app.run()
}