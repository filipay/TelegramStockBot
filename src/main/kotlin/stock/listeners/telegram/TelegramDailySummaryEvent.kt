package stock.listeners.telegram

import ifTrue
import messaging.BotConfig
import messaging.TelegramBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import stock.processor.StockEvent
import java.time.Duration
import java.time.Instant

class TelegramDailySummaryEvent(
    private var previousEvent: Instant,
    private val period: Duration,
    private val bot: TelegramBot
    ): TelegramEvent<StockEvent> {

    override fun onEvent(event: StockEvent) {
        val message = SendMessage.builder()
            .chatId(BotConfig.CHAT_TOKEN)
            .text("${event.stock}")
            .build()
        bot.execute(message)
    }

    override fun accept(event: StockEvent): Boolean =
        (Duration.between(previousEvent, event.instant).toMillis() > period.toMillis()).ifTrue {
            previousEvent = event.instant
        }
}