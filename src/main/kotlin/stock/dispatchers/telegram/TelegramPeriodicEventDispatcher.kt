package stock.dispatchers.telegram

import ifTrue
import messaging.TelegramBotMessenger
import org.apache.logging.log4j.LogManager
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import stock.processor.StockEvent
import java.time.Duration
import java.time.Instant
import java.util.Date

class TelegramPeriodicEventDispatcher(
    private var previousEvent: Instant,
    private val period: Duration,
    private val messenger: TelegramBotMessenger
): ConditionalEventDispatcher<StockEvent> {
    private val logger = LogManager.getLogger(TelegramPeriodicEventDispatcher::class.java)
    private val stockEvents = mutableMapOf<String, Instant>()
    override fun onEvent(event: StockEvent) {
        val message = SendMessage.builder()
            .chatId(messenger.getChatId())
            .text("${event.stock.name}: ${event.stock.quote}")
            .build()
        messenger.executeAsync(message)
    }

    override fun accept(event: StockEvent): Boolean =
        (Duration.between(stockEvents[event.stock.name] ?: previousEvent, event.instant).toMillis() > period.toMillis())
            .ifTrue {
                logger.info("Period: ${period.toMinutes()} min, date: ${Date(event.instant.toEpochMilli())}")
                stockEvents[event.stock.name] = event.instant
            }

}