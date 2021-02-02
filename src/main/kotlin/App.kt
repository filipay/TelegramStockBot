import com.google.gson.Gson
import configuration.ConfigProvider
import messaging.Emojis.DIAMOND
import messaging.Emojis.MOON
import messaging.Emojis.OPEN_HANDS
import messaging.Emojis.RAISE_HANDS
import messaging.Emojis.ROCKET
import messaging.Emojis.TOILET_PAPER
import messaging.TelegramBot
import messaging.handlers.commands.CommandTelegramHandler
import messaging.handlers.commands.StockCommandTelegramHandler
import messaging.handlers.text.TriggerWordHandler
import messaging.handlers.text.TextTelegramHandler
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import stock.adapter.YahooFinanceAdapter
import stock.listeners.telegram.TelegramPeriodicEvent
import stock.listeners.telegram.TelegramTimeRestrictedEvent
import stock.listeners.telegram.TelegramEventListener
import stock.listeners.telegram.TelegramMarketClosedEvent
import stock.processor.Event
import stock.processor.YahooStockProcessor
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.util.Calendar
import java.util.TimeZone
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.random.Random

class App(
    private val clock: Clock,
    private val processor: YahooStockProcessor,
    private val pollingFrequency: Duration
) {
    fun run() {
        println("${this.javaClass}: Running")
        Timer().schedule(0, pollingFrequency.toMillis()) {
            processor.process(Event(clock.instant()))
        }
    }
}

fun main() {
    val random = Random.Default
    val clock = Clock.systemUTC()
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    val yahooFinanceAdaptor = YahooFinanceAdapter()
    val config = ConfigProvider(Gson()).getConfig("${System.getProperty("user.dir")}/src/main/resources/config.json")
    val paperHands = TriggerWordHandler(
        setOf("sell","sold","thinking","selling", "sell?", "read", "reading"),
        listOf("$TOILET_PAPER$OPEN_HANDS","HOLD AND BUY ONLY $ROCKET$ROCKET", "<user> \"PAPER HANDS\" $TOILET_PAPER$OPEN_HANDS"),
        random
    )
    val diamondHands = TriggerWordHandler(
        setOf("buy","buy?","buying","bought","got","yolo","hold", "hodl"),
        listOf("$DIAMOND$RAISE_HANDS", "$ROCKET$ROCKET$ROCKET$MOON$MOON"),
        random
    )
    val wallyResponse = TriggerWordHandler(
        setOf("amc", "silver", "ref", "ref?", "reference", "source"),
        listOf("Ah get the boat", "Rats out", "Suck a poo", "Such a SCAM!", "GME TO THE MOOON BBY $ROCKET$ROCKET$ROCKET$MOON$MOON"),
        random
    )
    val stockCommandTelegramHandler = StockCommandTelegramHandler(yahooFinanceAdaptor)
    val telegramHandlers = listOf(
        TextTelegramHandler(listOf(paperHands, diamondHands, wallyResponse)),
        CommandTelegramHandler(mapOf(
            "/stock" to stockCommandTelegramHandler,
            "/stonk" to stockCommandTelegramHandler
        ))
    )
    val telegramBot = TelegramBot(config, telegramHandlers)
    val telegramBotsAPI = TelegramBotsApi(DefaultBotSession::class.java)
    telegramBotsAPI.registerBot(telegramBot)
    val telegram10MinuteUpdate = TelegramPeriodicEvent(Instant.now(), Duration.ofMinutes(10), telegramBot, config)
    val telegram30MinuteUpdate = TelegramPeriodicEvent(Instant.now(), Duration.ofMinutes(30), telegramBot, config)
    val telegramDayMarketUpdateEvent = TelegramTimeRestrictedEvent(calendar, 14..20, telegram10MinuteUpdate)
    val telegramAfterMarketUpdateEvent = TelegramTimeRestrictedEvent(calendar, 21..23, telegram30MinuteUpdate)
    val telegramPreMarketUpdateEvent = TelegramTimeRestrictedEvent(calendar, 9..13, telegram30MinuteUpdate)
    val telegramEventListener = TelegramEventListener(listOf(telegramDayMarketUpdateEvent, telegramAfterMarketUpdateEvent, telegramPreMarketUpdateEvent))
    val processor = YahooStockProcessor(listOf("GME", "BB"), listOf(telegramEventListener), yahooFinanceAdaptor)
    val app = App(clock, processor, Duration.ofSeconds(2))
    app.run()
}