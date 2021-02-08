package functional

import App
import io.mockk.every
import io.mockk.mockk
import messaging.TelegramBotMessenger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import stock.adapter.YahooFinanceAdapter
import java.time.Clock
import java.time.Instant
import java.util.Calendar
import java.util.Timer


@ExtendWith(SpringExtension::class)
@ContextConfiguration("classpath:application.xml")
open class BaseFunctionalTest {
    @Autowired
    protected lateinit var app: App

    @Autowired
    protected lateinit var timer: Timer

    @Autowired
    protected lateinit var messenger: TelegramBotMessenger

    @Autowired
    protected lateinit var adapter: YahooFinanceAdapter

    @Autowired
    protected lateinit var calendar: Calendar

    @Autowired
    protected lateinit var clock: Clock

    @Autowired
    protected lateinit var now: Instant

    @BeforeEach
    fun setup() {
        every { timer.schedule(any(), any<Long>(), any()) } answers {
            (firstArg() as Runnable).run()
        }

        every { messenger.execute(any<SendMessage>()) } returns mockk()

        every { adapter.stocks(any()) } answers {
            mapOf("GME" to mockk {
                every { name } returns "GME"
                every { quote } returns mockk(relaxed = true)
            })
        }
    }
}