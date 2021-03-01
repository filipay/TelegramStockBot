import exchanges.Event
import exchanges.processor.Processor
import org.apache.logging.log4j.LogManager
import org.springframework.context.support.ClassPathXmlApplicationContext
import java.time.Clock
import java.time.Duration
import java.util.Timer
import kotlin.concurrent.schedule

class App(
    private val timer: Timer,
    private val clock: Clock,
    private val processors: List<Processor>,
    private val pollingFrequency: Duration
) {
    private val logger = LogManager.getLogger(App::class.java)

    fun run() {
        logger.info("Starting processing with polling frequency of: ${pollingFrequency.seconds}s")
        timer.schedule(0, pollingFrequency.toMillis()) {
            runCatching {
                processors.forEach { it.process(Event(clock.instant())) }
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