package stock.listeners.telegram

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import stock.processor.StockEvent
import java.util.Calendar

internal class TelegramTimeRestrictedEventTest {
    private val calendar: Calendar = mockk(relaxed = true)
    private val delegate: TelegramEvent<StockEvent> = mockk(relaxed = true)
    private val telegramDayMarketEvent = TelegramTimeRestrictedEvent(calendar, 14..21, delegate)

    @Test
    fun `should accept if within day time trading hours`() {
        every { calendar.get(any()) } returns 14
        every { delegate.accept(any()) } returns true
        assertTrue(telegramDayMarketEvent.accept(StockEvent(mockk(), mockk())))
    }

    @Test
    fun `should reject if outside of day time trading hours`() {
        every { calendar.get(any()) } returns 11
        assertFalse(telegramDayMarketEvent.accept(StockEvent(mockk(), mockk())))
    }
}