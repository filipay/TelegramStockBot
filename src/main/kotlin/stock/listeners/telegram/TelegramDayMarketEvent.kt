package stock.listeners.telegram

import stock.processor.StockEvent
import java.util.Calendar

class TelegramDayMarketEvent(private val calendar: Calendar): TelegramEvent<StockEvent> {
    override fun onEvent(event: StockEvent) = Unit

    override fun accept(event: StockEvent): Boolean = calendar.get(Calendar.HOUR_OF_DAY) in 14..21
}