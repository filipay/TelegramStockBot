package stock.listeners.telegram

import stock.processor.StockEvent
import java.util.*

class TelegramMarketClosedEvent(private val calendar: Calendar) : TelegramEvent<StockEvent> {
    companion object {
        const val MARKET_CLOSE_UTC = 15
    }
    private var dailyEventTriggered = false
    override fun onEvent(event: StockEvent) {
    }

    override fun accept(event: StockEvent): Boolean {
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        return when {
            hourOfDay == MARKET_CLOSE_UTC && !dailyEventTriggered -> true
            hourOfDay != MARKET_CLOSE_UTC && dailyEventTriggered -> false
            else -> false
        }.also { dailyEventTriggered = it }
    }
}