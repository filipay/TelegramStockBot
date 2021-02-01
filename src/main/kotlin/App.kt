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
    val locale = Locale("en", "IE")
    val timeZone = TimeZone.getTimeZone("Europe/Dublin")

    val clock = Clock.systemUTC()
    val cal = Calendar.getInstance(timeZone, locale)

    val telegramBotsAPI = TelegramBotsApi(DefaultBotSession::class.java)
    val telegramBot = TelegramBot()
    telegramBotsAPI.registerBot(telegramBot)
    val calendar = Calendar.getInstance()
    val telegramDailySummaryEvent = TelegramDailySummaryEvent(Instant.now(), Duration.ofSeconds(5), telegramBot)
    val telegramMarketClosedEvent = TelegramMarketClosedEvent(calendar)
    val telegramEventListener = TelegramEventListener(listOf(telegramDailySummaryEvent, telegramMarketClosedEvent))
    val yahooFinanceAdaptor = YahooFinanceAdapter()
    val processor = YahooStockProcessor(listOf("GME"), listOf(telegramEventListener), yahooFinanceAdaptor)
    var app = when(cal.get(Calendar.HOUR_OF_DAY)){
        in 14..21 -> App(clock, processor, Duration.ofSeconds(900))
        else -> App(clock, processor, Duration.ofSeconds(9000000))
    }

    app.run()
}