import org.apache.logging.log4j.LogManager
import org.springframework.context.support.ClassPathXmlApplicationContext
import stock.processor.Event
import stock.processor.YahooStockProcessor
import java.time.Clock
import java.time.Duration
import java.util.Timer
import kotlin.concurrent.schedule

class App(
    private val timer: Timer,
    private val clock: Clock,
    private val processor: YahooStockProcessor,
    private val pollingFrequency: Duration
) {
    private val logger = LogManager.getLogger(App::class.java)

    fun run() {
        logger.info("Starting processing with polling frequency of: ${pollingFrequency.seconds}s")
        timer.schedule(0, pollingFrequency.toMillis()) {
            runCatching {
                processor.process(Event(clock.instant()))
            }.onFailure {
                logger.error("Process failed", it)
            }
        }
    }
}

fun main() {
    val context = ClassPathXmlApplicationContext("classpath:spring-configuration/application.xml")
    val app = context.getBean("app", App::class.java)
    app.run()
}