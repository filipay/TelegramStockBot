package exchanges.processor

import exchanges.Event
import exchanges.TickerEvent
import exchanges.adapter.TickerAdapter
import exchanges.dispatchers.EventDispatcher
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.Instant

internal class TickerProcessorTest {
    private val eventDispatcher1: EventDispatcher<TickerEvent> = mockk(relaxed = true)
    private val eventDispatcher2: EventDispatcher<TickerEvent> = mockk(relaxed = true)
    private val tickerAdapter: TickerAdapter = mockk {
        every { tickers(any()) } returns listOf(mockk(relaxed = true))
    }
    private val processor = TickerProcessor(listOf("POO"), listOf(eventDispatcher1, eventDispatcher2), tickerAdapter)

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