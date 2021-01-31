package stock.listeners.telegram

import TelegramBot
import ifTrue
import stock.processor.StockEvent
import java.net.URLEncoder
import java.time.Duration
import java.time.Instant

class TelegramDailySummaryEvent(
    private var previousEvent: Instant,
    private val period: Duration,
    private val bot: TelegramBot
    ): TelegramEvent<StockEvent> {

    override fun onEvent(event: StockEvent) {
        val stockPrice = URLEncoder.encode(event.stock.toString().replace(".", "\\."), "utf-8")
        bot.sendMessage(stockPrice)
    }

    override fun accept(event: StockEvent): Boolean =
        (Duration.between(previousEvent, event.instant).toMillis() > period.toMillis()).ifTrue {
            previousEvent = event.instant
        }
}