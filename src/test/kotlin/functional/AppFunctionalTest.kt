package functional

import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import java.time.Duration
import java.time.Instant
import java.util.Calendar

class AppFunctionalTest: BaseFunctionalTest() {
    @Test
    fun `should emit a day time event`() {
        val messages = mutableListOf<SendMessage>()
        every { calendar.get(Calendar.HOUR_OF_DAY)} returns 14
        every { clock.instant() } returns Instant.ofEpochMilli(Duration.ofHours(1).toMillis())

        app.run()

        verify { messenger.executeAsync(capture(messages)) }
        assertEquals(1, messages.size)
    }

    @Test
    fun `should emit an after market time event`() {
        val messages = mutableListOf<SendMessage>()
        every { calendar.get(Calendar.HOUR_OF_DAY)} returns 22
        every { clock.instant() } returns Instant.ofEpochMilli(Duration.ofHours(1).toMillis())

        app.run()

        verify { messenger.executeAsync(capture(messages)) }
        assertEquals(1, messages.size)
    }
}