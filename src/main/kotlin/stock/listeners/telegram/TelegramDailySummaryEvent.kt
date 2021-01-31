package stock.listeners.telegram

import ifTrue
import stock.processor.StockEvent
import java.time.Duration
import java.time.Instant

class TelegramDailySummaryEvent(
    private var previousEvent: Instant,
    private val period: Duration
    ): TelegramEvent<StockEvent> {

    override fun onEvent(event: StockEvent) {
        println("Telegram daily summary")
    }

    override fun accept(event: StockEvent): Boolean =
        (Duration.between(previousEvent, event.instant).toMillis() > period.toMillis()).ifTrue {
            previousEvent = event.instant
        }
}