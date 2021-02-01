import messaging.TelegramBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import stock.adapter.YahooFinanceAdapter
import stock.listeners.telegram.TelegramDailySummaryEvent
import stock.listeners.telegram.TelegramEventListener
import stock.listeners.telegram.TelegramMarketClosedEvent
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
            processor.process(Event(clock.instant()))
        }
    }
}

fun main() {
    val telegramBotsAPI = TelegramBotsApi(DefaultBotSession::class.java)
    val telegramBot = TelegramBot()
    telegramBotsAPI.registerBot(telegramBot)
    val clock = Clock.systemUTC()
    val calendar = Calendar.getInstance()
    val telegramDailySummaryEvent = TelegramDailySummaryEvent(Instant.now(), Duration.ofSeconds(5), telegramBot)
    val telegramMarketClosedEvent = TelegramMarketClosedEvent(calendar)
    val telegramEventListener = TelegramEventListener(listOf(telegramDailySummaryEvent, telegramMarketClosedEvent))
    val yahooFinanceAdaptor = YahooFinanceAdapter()
    val processor = YahooStockProcessor(listOf("GME"), listOf(telegramEventListener), yahooFinanceAdaptor)
    val app = App(clock, processor, Duration.ofSeconds(2))
    app.run()
}