package functional

import App
import exchanges.adapter.KrakenExchangeAdapter
import exchanges.adapter.YahooFinanceAdapter
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import messaging.TelegramBotMessenger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import java.time.Clock
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
    protected lateinit var yahooFinanceAdapter: YahooFinanceAdapter

    @Autowired
    protected lateinit var krakenExchangeAdapter: KrakenExchangeAdapter

    @Autowired
    protected lateinit var clock: Clock

    @BeforeEach
    fun setup() {
        clearMocks(messenger)

        every { timer.schedule(any(), any<Long>(), any()) } answers {
            (firstArg() as Runnable).run()
        }
        every { messenger.execute(any<SendMessage>()) } returns mockk()
        every { yahooFinanceAdapter.stocks(any()) } returns mapOf("GME" to mockk(relaxed = true) {
            every { name } returns "GME"
        })
        every { krakenExchangeAdapter.cryptos(any()) } returns listOf(mockk(relaxed = true) {
            every { name } returns "BTC/EUR"
        })
    }
}