package stock.listeners.telegram

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import stock.processor.StockEvent
import java.util.Calendar

internal class TelegramDayMarketEventTest {
    private val calendar: Calendar = mockk(relaxed = true)
    private val telegramDayMarketEvent = TelegramDayMarketEvent(calendar)

    @Test
    fun `should accept if within day time trading hours`() {
        every { calendar.get(any()) } returns 14
        assertTrue(telegramDayMarketEvent.accept(StockEvent(mockk(), mockk())))
    }

    @Test
    fun `should reject if outside of day time trading hours`() {
        every { calendar.get(any()) } returns 11
        assertFalse(telegramDayMarketEvent.accept(StockEvent(mockk(), mockk())))
    }
}