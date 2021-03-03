package exchanges.dispatchers.telegram

import exchanges.Ticker
import exchanges.TickerEvent
import ifTrue
import messaging.Formatter
import messaging.TelegramBotMessenger
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.message.SimpleMessage
import org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWNV2
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class TelegramPeriodicEventDispatcher(
    private var previousEvent: Instant,
    private val period: Duration,
    private val messenger: TelegramBotMessenger,
    private val formatter: Formatter<Ticker>
): ConditionalEventDispatcher<TickerEvent> {
    private val logger = LogManager.getLogger(TelegramPeriodicEventDispatcher::class.java)
    private val stockEvents = mutableMapOf<String, Instant>()
    override fun onEvent(event: TickerEvent) {
        logger.debug { SimpleMessage("Sending message for $event") }
        val message = SendMessage.builder()
            .chatId(messenger.getChatId())
            .parseMode(MARKDOWNV2)
            .text(formatter.format(event.ticker))
            .build()
        messenger.execute(message)
    }

    override fun accept(event: TickerEvent): Boolean =
        (Duration.between(stockEvents[event.ticker.name] ?: previousEvent, event.instant).toMillis() > period.toMillis())
            .ifTrue {
                logger.info("Period: ${period.toMinutes()} min, date: ${LocalDateTime.ofInstant(event.instant, ZoneId.of("UTC"))}")
                stockEvents[event.ticker.name] = event.instant
            }

}

class TelegramPeriodicEventDispatcherFactory(
    private var previousEvent: Instant,
    private val messenger: TelegramBotMessenger,
    private val formatter: Formatter<Ticker>
) {
    fun create(period: Duration) = TelegramPeriodicEventDispatcher(previousEvent, period, messenger, formatter)

    fun create(periodInMinutes: Long) = create(Duration.ofMinutes(periodInMinutes))
}