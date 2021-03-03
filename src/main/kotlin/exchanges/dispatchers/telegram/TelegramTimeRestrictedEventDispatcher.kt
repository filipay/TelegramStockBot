package exchanges.dispatchers.telegram

import exchanges.TickerEvent
import ifFalse
import ifTrue
import org.apache.logging.log4j.LogManager
import java.time.LocalDateTime
import java.time.ZoneId

class TelegramTimeRestrictedEventDispatcher(
    private val range: IntRange,
    private val delegate: ConditionalEventDispatcher<TickerEvent>
): ConditionalEventDispatcher<TickerEvent> by delegate {
    private val logger = LogManager.getLogger(TelegramTimeRestrictedEventDispatcher::class.java)

    override fun accept(event: TickerEvent): Boolean {
        val dateTime = LocalDateTime.ofInstant(event.instant, ZoneId.of("UTC"))
        return (dateTime.hour in range).ifTrue {
            logger.info("ACCEPTED: Range: $range, Hour: ${dateTime.hour}")
        }.ifFalse {
            logger.info("DENIED: Range: $range, Hour: ${dateTime.hour}")
        } && delegate.accept(event)
    }
}
