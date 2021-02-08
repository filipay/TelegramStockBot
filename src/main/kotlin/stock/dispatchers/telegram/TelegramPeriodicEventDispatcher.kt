package stock.dispatchers.telegram

import ifTrue
import messaging.TelegramBotMessenger
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import stock.processor.StockEvent
import java.time.Duration
import java.time.Instant

class TelegramPeriodicEventDispatcher(
    private var previousEvent: Instant,
    private val period: Duration,
    private val messenger: TelegramBotMessenger
): ConditionalEventDispatcher<StockEvent> {
    private val stockEvents = mutableMapOf<String, Instant>()
    override fun onEvent(event: StockEvent) {
        val message = SendMessage.builder()
            .chatId(messenger.getChatId())
            .text("${event.stock.name}: ${event.stock.quote}")
            .build()
        messenger.execute(message)
    }

    override fun accept(event: StockEvent): Boolean =
        (Duration.between(stockEvents[event.stock.name] ?: previousEvent, event.instant).toMillis() > period.toMillis())
            .ifTrue {
                stockEvents[event.stock.name] = event.instant
            }

}