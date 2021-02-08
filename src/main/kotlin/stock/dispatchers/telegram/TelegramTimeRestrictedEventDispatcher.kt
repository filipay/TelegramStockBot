package stock.dispatchers.telegram

import stock.processor.StockEvent
import java.util.Calendar

class TelegramTimeRestrictedEventDispatcher(
    private val calendar: Calendar,
    private val range: IntRange,
    private val delegate: ConditionalEventDispatcher<StockEvent>
): ConditionalEventDispatcher<StockEvent> by delegate {
    override fun accept(event: StockEvent): Boolean =
        delegate.accept(event) && calendar.get(Calendar.HOUR_OF_DAY) in range
}