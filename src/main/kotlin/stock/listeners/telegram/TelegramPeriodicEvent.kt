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

    override fun onEvent(event: StockEvent) {
        val message = SendMessage.builder()
            .chatId(config.chatId)
            .text("${event.stock.name}: ${event.stock.quote}")
            .build()
        bot.execute(message)
    }

    override fun accept(event: StockEvent): Boolean =
        (Duration.between(previousEvent, event.instant).toMillis() > period.toMillis()).ifTrue {
            previousEvent = event.instant
        }

}