package stock.dispatchers.telegram

import ifTrue
import org.apache.logging.log4j.LogManager
import stock.processor.StockEvent
import java.util.Calendar

class TelegramTimeRestrictedEventDispatcher(
    private val calendar: Calendar,
    private val range: IntRange,
    private val delegate: ConditionalEventDispatcher<StockEvent>
): ConditionalEventDispatcher<StockEvent> by delegate {
    private val logger = LogManager.getLogger(TelegramTimeRestrictedEventDispatcher::class.java)

    override fun accept(event: StockEvent): Boolean =
        delegate.accept(event) && (calendar.get(Calendar.HOUR_OF_DAY) in range).ifTrue {
            logger.info("Range: $range, Hour: ${calendar.get(Calendar.HOUR_OF_DAY)}")
        }
}