package stock.processor

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import stock.adapter.YahooFinanceAdapter
import stock.listeners.EventListener
import java.time.Instant

internal class YahooStockProcessorTest {
    private val eventListener1: EventListener<StockEvent> = mockk(relaxed = true)
    private val eventListener2: EventListener<StockEvent> = mockk(relaxed = true)
    private val yahooFinanceAdapter: YahooFinanceAdapter = mockk {
        every { stocks(any()) } returns mapOf("POO" to mockk())
    }
    private val processor = YahooStockProcessor(listOf("POO"), listOf(eventListener1, eventListener2), yahooFinanceAdapter)

    @Test
    fun `should call listeners about the event`() {
        val instant = Instant.ofEpochMilli(0)
        processor.process(Event(instant))
        verify(exactly = 1) {
            eventListener1.onEvent(any())
            eventListener2.onEvent(any())
        }
    }
}