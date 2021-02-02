package stock.listeners.telegram

import configuration.Config
import ifTrue
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import stock.processor.StockEvent
import java.time.Duration
import java.time.Instant

class TelegramPeriodicEvent(
    private var previousEvent: Instant,
    private val period: Duration,
    private val bot: TelegramLongPollingBot,
    private val config: Config
): TelegramEvent<StockEvent> {
    private val stockEvents = mutableMapOf<String, Instant>()
    override fun onEvent(event: StockEvent) {
        val message = SendMessage.builder()
            .chatId(config.chatId)
            .text("${event.stock.name}: ${event.stock.quote}")
            .build()
        bot.execute(message)
    }

    override fun accept(event: StockEvent): Boolean =
        (Duration.between(stockEvents[event.stock.name] ?: previousEvent, event.instant).toMillis() > period.toMillis())
            .ifTrue {
                stockEvents[event.stock.name] = event.instant
            }

}