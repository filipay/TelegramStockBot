package stock.listeners.telegram

import ifTrue
import messaging.BotConfig
import messaging.TelegramBot
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import stock.processor.StockEvent
import java.time.Duration
import java.time.Instant
import java.util.Calendar

class TelegramDailyPeriodicEvent(
    private var previousEvent: Instant,
    private val period: Duration,
    private val bot: TelegramLongPollingBot,
    private val telegramDayMarketEvent: TelegramEvent<StockEvent>
): TelegramEvent<StockEvent> {

    override fun onEvent(event: StockEvent) {
        val message = SendMessage.builder()
            .chatId(BotConfig.CHAT_TOKEN)
            .text("${event.stock.name}: ${event.stock.quote}")
            .build()
        bot.execute(message)
    }

    override fun accept(event: StockEvent): Boolean =
        telegramDayMarketEvent.accept(event) && (Duration.between(previousEvent, event.instant).toMillis() > period.toMillis()).ifTrue {
            previousEvent = event.instant
        }

}