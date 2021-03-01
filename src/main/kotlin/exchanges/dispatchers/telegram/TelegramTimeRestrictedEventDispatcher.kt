package exchanges.dispatchers.telegram

import exchanges.TickerEvent
import ifFalse
import ifTrue
import org.apache.logging.log4j.LogManager
import java.util.Calendar

class TelegramTimeRestrictedEventDispatcher(
    private val calendar: Calendar,
    private val range: IntRange,
    private val delegate: ConditionalEventDispatcher<TickerEvent>
): ConditionalEventDispatcher<TickerEvent> by delegate {
    private val logger = LogManager.getLogger(TelegramTimeRestrictedEventDispatcher::class.java)

    override fun accept(event: TickerEvent): Boolean =
        delegate.accept(event) && (calendar.get(Calendar.HOUR_OF_DAY) in range).ifTrue {
            logger.info("ACCEPTED: Range: $range, Hour: ${calendar.get(Calendar.HOUR_OF_DAY)}")
        }.ifFalse {
            logger.info("DENIED: Range: $range, Hour: ${calendar.get(Calendar.HOUR_OF_DAY)}")
        }
}