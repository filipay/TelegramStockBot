package functional

import io.mockk.Called
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import java.time.Duration
import java.time.Instant

class AppFunctionalTest: BaseFunctionalTest() {
    @Test
    fun `should emit a day time event`() {
        val messages = mutableListOf<SendMessage>()
        every { clock.instant() } returns Instant.ofEpochMilli(Duration.ofHours(14).toMillis())

        app.run()

        verify(exactly = 2) { messenger.execute(capture(messages)) }
        assertEquals(2, messages.size)
    }

    @Test
    fun `should emit an after market time event`() {
        val messages = mutableListOf<SendMessage>()
        every { clock.instant() } returns Instant.ofEpochMilli(Duration.ofHours(22).toMillis())

        app.run()

        verify(exactly = 2) { messenger.execute(capture(messages)) }
        assertEquals(2, messages.size)
    }

    @Test
    fun `should emit a pre market time event`() {
        val messages = mutableListOf<SendMessage>()
        every { clock.instant() } returns Instant.ofEpochMilli(Duration.ofHours(10).toMillis())

        app.run()

        verify(exactly = 2) { messenger.execute(capture(messages)) }
        assertEquals(2, messages.size)
    }

    @Test
    fun `should not emit any thing during the night`() {
        val messages = mutableListOf<SendMessage>()
        every { clock.instant() } returns Instant.ofEpochMilli(Duration.ofHours(2).toMillis())

        app.run()

        verify { messenger.execute(capture(messages)) wasNot Called }
        assertEquals(0, messages.size)
    }
}