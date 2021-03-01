package exchanges.processor

import exchanges.Event
import exchanges.TickerEvent
import exchanges.adapter.YahooFinanceAdapter
import exchanges.dispatchers.EventDispatcher
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.Instant

internal class YahooStockProcessorTest {
    private val eventDispatcher1: EventDispatcher<TickerEvent> = mockk(relaxed = true)
    private val eventDispatcher2: EventDispatcher<TickerEvent> = mockk(relaxed = true)
    private val yahooFinanceAdapter: YahooFinanceAdapter = mockk {
        every { stocks(any()) } returns mapOf("POO" to mockk())
    }
    private val processor = YahooStockProcessor(listOf("POO"), listOf(eventDispatcher1, eventDispatcher2), yahooFinanceAdapter)

    @Test
    fun `should call listeners about the event`() {
        val instant = Instant.ofEpochMilli(0)
        processor.process(Event(instant))
        verify(exactly = 1) {
            eventDispatcher1.onEvent(any())
            eventDispatcher2.onEvent(any())
        }
    }
}