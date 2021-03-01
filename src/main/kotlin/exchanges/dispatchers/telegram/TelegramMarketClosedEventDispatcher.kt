package exchanges.dispatchers.telegram

import exchanges.TickerEvent
import java.util.Calendar

class TelegramMarketClosedEventDispatcher(private val calendar: Calendar) : ConditionalEventDispatcher<TickerEvent> {
    companion object {
        const val MARKET_CLOSE_UTC = 15
    }
    private var dailyEventTriggered = false
    override fun onEvent(event: TickerEvent) = Unit

    override fun accept(event: TickerEvent): Boolean {
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        return when {
            hourOfDay == MARKET_CLOSE_UTC && !dailyEventTriggered -> true
            hourOfDay != MARKET_CLOSE_UTC && dailyEventTriggered -> false
            else -> false
        }.also { dailyEventTriggered = it }
    }
}